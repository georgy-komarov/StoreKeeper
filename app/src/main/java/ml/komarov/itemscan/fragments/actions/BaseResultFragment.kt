package ml.komarov.itemscan.fragments.actions

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.App
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.FragmentResultBinding
import ml.komarov.itemscan.db.AppDatabase
import ml.komarov.itemscan.db.BarcodeWithInfo
import ml.komarov.itemscan.db.Product
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray


abstract class BaseResultFragment : Fragment(), MenuProvider {

    private lateinit var _resultBinding: FragmentResultBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var waitDialog: AlertDialog
    private lateinit var product: Product

    protected val productDataList = ArrayList<BaseActionUnit>()

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        // FIXME
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Make sure to create the concrete binding while it's required to
        // create the resultBinding from it
        val binding = onCreateViewBinding(inflater, container)

        // We're using the concrete layout of the child class to create our
        // commonly used binding
        _resultBinding = FragmentResultBinding.bind(binding.root)
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.app_settings),
            Context.MODE_PRIVATE
        )
        waitDialog = createWaitDialog()

        setup()
        setupMenu()

        return binding.root
    }

    // Makes sure to create the concrete binding class from child-classes before
    // the commonBinding can be bound
    protected abstract fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewBinding

    // Allows child-classes to access the resultBinding to access common
    // used views
    private val resultBinding get() = _resultBinding

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_close, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_close -> {
                findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
                    .popBackStack(menuCloseDestination, false)
                true
            }
            else -> false
        }
    }

    abstract val menuCloseDestination: Int

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun createWaitDialog(): AlertDialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.receiving_data)
        builder.setMessage(R.string.please_wait)
        builder.setCancelable(false)
        return builder.create()
    }

    private fun getProductData(productKey: String) {
        val accessToken = sharedPreferences.getString("onesToken", "")

        val baseUrl = sharedPreferences.getString("onesAddress", "")?.replace("/+$", "")
        val dataUrl = "$baseUrl/api/products/$productKey/prices"

        val request = Request.Builder()
            .url(dataUrl)
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        // Synchronous request here
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                requireActivity().runOnUiThread {
                    waitDialog.hide()
                    Toast.makeText(
                        requireContext(),
                        "${getString(R.string.failed_to_get_response)} (${response.code})",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                val responseData = response.body!!.string()
                val jsonList = JSONArray(responseData)
                val db: AppDatabase = App.instance!!.database

                // Parse response
                for (i in 0 until jsonList.length()) {
                    val productDataJSON = jsonList.getJSONObject(i)
                    val characteristicKey = productDataJSON.getString("characteristic_id")
                    val productData = BaseActionUnit(
                        characteristicKey,
                        db.characteristicDao().findByKey(characteristicKey).name,
                        productDataJSON.getInt("price"),
                        productDataJSON.getInt("amount"),
                    )
                    productDataList.add(productData)
                }

                requireActivity().runOnUiThread {
                    waitDialog.hide()
                }
            }
        }
    }

    protected open fun setup() {
        waitDialog.show()

        val db: AppDatabase = App.instance!!.database

        // Parse arguments
        val productKey = requireArguments().getString("productKey", "")
        val barcodeData = requireArguments().getString("barcode")

        product = db.productDao().findByKey(productKey)

        var barcode: BarcodeWithInfo? = null
        if (barcodeData !== null) {
            barcode = db.barcodeDao().findByBarcodeWithInfo(barcodeData)[0]
        }

        resultBinding.textCode.text = barcodeData?.ifBlank { "-" }
        resultBinding.textProduct.text = product.name
        resultBinding.textSku.text = product.sku
        resultBinding.textSize.text = barcode?.characteristic?.name?.ifBlank { "-" }

        getProductData(productKey)
    }
}