package ml.komarov.itemscan.fragments.actions

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation.findNavController
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.App
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.FragmentResultBinding
import ml.komarov.itemscan.db.AppDatabase
import ml.komarov.itemscan.db.BarcodeWithInfo
import ml.komarov.itemscan.db.Product


abstract class BaseResultFragment : Fragment(), MenuProvider {

    private lateinit var _resultBinding: FragmentResultBinding
    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Make sure to create the concrete binding while it's required to
        // create the resultBinding from it
        val binding = onCreateViewBinding(inflater, container)

        // We're using the concrete layout of the child class to create our
        // commonly used binding
        _resultBinding = FragmentResultBinding.bind(binding.root)

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

    protected open fun setup() {
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
    }
}