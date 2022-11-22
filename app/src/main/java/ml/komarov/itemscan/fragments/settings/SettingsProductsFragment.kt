package ml.komarov.itemscan.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import ml.komarov.itemscan.App
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.FragmentSettingsProductsBinding
import ml.komarov.itemscan.db.AppDatabase
import ml.komarov.itemscan.db.Barcode
import ml.komarov.itemscan.db.Characteristic
import ml.komarov.itemscan.db.Product
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class SettingsProductsFragment : Fragment() {

    private var _binding: FragmentSettingsProductsBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val client = OkHttpClient()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsProductsBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.app_settings),
            Context.MODE_PRIVATE
        )

        setup()

        return binding.root
    }

    private fun setup() {
        loadProductsSettings()
        loadDbCounters()

        binding.buttonSaveProductsSettings.setOnClickListener {
            saveProductsSettings()
        }
        binding.buttonUpdateProducts.setOnClickListener {
            syncData()
        }
    }

    private fun loadDbCounters() {
        val db: AppDatabase = App.instance!!.database

        binding.textProductsCount.text = db.productDao().count().toString()
        binding.textSizesCount.text = db.characteristicDao().count().toString()
        binding.textBarcodesCount.text = db.barcodeDao().count().toString()
    }

    private fun loadProductsSettings() {
        binding.editTextOnesAddress.editText?.setText(
            sharedPreferences.getString("onesAddress", "")
        )
        binding.editTextOnesUsername.editText?.setText(
            sharedPreferences.getString("onesUsername", "")
        )
        binding.editTextOnesPassword.editText?.setText(
            sharedPreferences.getString("onesPassword", "")
        )
    }

    private fun saveProductsSettings() {
        val address = binding.editTextOnesAddress.editText?.text.toString()
        val username = binding.editTextOnesUsername.editText?.text.toString()
        val password = binding.editTextOnesPassword.editText?.text.toString()

        sharedPreferences.edit()
            .putString("onesAddress", address)
            .putString("onesUsername", username)
            .putString("onesPassword", password)
            .apply()

        val baseUrl = binding.editTextOnesAddress.editText?.text.toString().replace("/+$", "")
        val authUrl = "$baseUrl/auth/jwt/create/"
        val authData = mapOf("email" to username, "password" to password)

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val authBody = Gson().toJson(authData).toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .post(authBody)
            .url(authUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        context,
                        R.string.failed_to_get_response,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(
                                requireContext(),
                                "${getString(R.string.failed_to_get_response)} (${response.code})",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        val responseData = response.body!!.string()
                        val jsonData = JSONObject(responseData)

                        val token = jsonData.getString("access")

                        sharedPreferences.edit()
                            .putString("onesToken", token)
                            .apply()
                    }
                }
            }
        })
    }

    private fun syncData() {
        val baseUrl = binding.editTextOnesAddress.editText?.text.toString().replace("/+$", "")
        val dataUrl = "$baseUrl/api/sync-data"

        val accessToken = sharedPreferences.getString("onesToken", "")

        val request = Request.Builder()
            .url(dataUrl)
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        context,
                        R.string.failed_to_get_response,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(
                                requireContext(),
                                "${getString(R.string.failed_to_get_response)} (${response.code})",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        val responseData = response.body!!.string()
                        val jsonData = JSONObject(responseData)

                        // Parse response
                        val productsData = jsonData.getJSONArray("products")
                        val characteristicsData = jsonData.getJSONArray("characteristics")
                        val barcodesData = jsonData.getJSONArray("barcodes")

                        val productsList = ArrayList<Product>()
                        val characteristicsList = ArrayList<Characteristic>()
                        val barcodesList = ArrayList<Barcode>()

                        val db: AppDatabase = App.instance!!.database

                        val productDao = db.productDao()
                        val characteristicDao = db.characteristicDao()
                        val barcodeDao = db.barcodeDao()

                        // Parse products
                        for (i in 0 until productsData.length()) {
                            val productJSON = productsData.getJSONObject(i)
                            val refKey = productJSON.getString("ref_key")
                            val name = productJSON.getString("name")
                            val sku = productJSON.getString("sku")

                            val product = Product(refKey, name, sku)

                            productsList.add(product)
                        }

                        // Save products to DB
                        productDao.insertAll(*productsList.map { it }.toTypedArray())

                        // Parse characteristics
                        for (i in 0 until characteristicsData.length()) {
                            val characteristicJSON = characteristicsData.getJSONObject(i)
                            val refKey = characteristicJSON.getString("ref_key")
                            val name = characteristicJSON.getString("name")

                            val characteristic = Characteristic(refKey, name)

                            characteristicsList.add(characteristic)
                        }

                        // Save characteristics to DB
                        characteristicDao.insertAll(*characteristicsList.map { it }.toTypedArray())

                        // Parse barcodes
                        for (i in 0 until barcodesData.length()) {
                            val barcodeJSON = barcodesData.getJSONObject(i)
                            val productKey = barcodeJSON.getString("product")
                            val characteristicKey = barcodeJSON.getString("characteristic")
                            val barcodeText = barcodeJSON.getString("barcode")

                            val barcode = Barcode(barcodeText, productKey, characteristicKey)

                            barcodesList.add(barcode)
                        }

                        // Save barcodes to DB
                        barcodeDao.insertAll(*barcodesList.map { it }.toTypedArray())

                        requireActivity().runOnUiThread {
                            loadDbCounters()
                        }
                    }
                }
            }
        })
    }
}