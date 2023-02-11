package ml.komarov.storekeeper.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import ml.komarov.storekeeper.R
import ml.komarov.storekeeper.databinding.FragmentSettingsPrinterBinding
import okhttp3.*
import okio.IOException
import org.json.JSONArray


class SettingsPrinterFragment : Fragment() {

    private var _binding: FragmentSettingsPrinterBinding? = null
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
        _binding = FragmentSettingsPrinterBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.app_settings),
            Context.MODE_PRIVATE
        )

        setup()

        return binding.root
    }

    private fun setup() {
        loadPrinterSettings()

        binding.buttonPrintUpdate.setOnClickListener {
            updatePrinters()
        }
        binding.buttonSavePrintSettings.setOnClickListener {
            savePrinterSettings()
        }
    }

    private fun loadPrinterSettings() {
        binding.editTextAddress.editText?.setText(
            sharedPreferences.getString("printerAddress", "")
        )
        binding.editTextPrinter.setText(
            sharedPreferences.getString("printerName", "")
        )
    }

    private fun savePrinterSettings() {
        sharedPreferences.edit()
            .putString("printerAddress", binding.editTextAddress.editText?.text.toString())
            .putString("printerName", binding.editTextPrinter.text.toString())
            .apply()
    }

    private fun updatePrinters() {
        val baseUrl = binding.editTextAddress.editText?.text.toString().replace("/+$", "")
        val listUrl = "$baseUrl/Integration/listPrinters/Execute"
        val request = Request.Builder()
            .url(listUrl)
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
                        val jsonList = JSONArray(responseData)
                        val printerList = ArrayList<String>()

                        for (i in 0 until jsonList.length()) {
                            printerList.add(jsonList.getString(i))
                        }
                        printerList.add("PDF") // BarTender Virtual Printer

                        val adapter =
                            ArrayAdapter(requireContext(), R.layout.list_item, printerList)

                        requireActivity().runOnUiThread {
                            binding.editTextPrinter.setAdapter(adapter)
                            binding.editTextPrinter.isEnabled = true
                            binding.editTextPrinter.showDropDown()
                        }
                    }
                }
            }
        })
    }
}