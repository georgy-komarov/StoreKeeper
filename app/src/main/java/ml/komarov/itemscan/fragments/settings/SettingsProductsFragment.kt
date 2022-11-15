package ml.komarov.itemscan.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ml.komarov.itemscan.App
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.FragmentSettingsProductsBinding
import ml.komarov.itemscan.db.AppDatabase


class SettingsProductsFragment : Fragment() {

    private var _binding: FragmentSettingsProductsBinding? = null
    private lateinit var sharedPreferences: SharedPreferences

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
            sharedPreferences.getString(
                "onesAddress",
                ""
            )
        )
        binding.editTextOnesUsername.editText?.setText(
            sharedPreferences.getString(
                "onesUsername",
                ""
            )
        )
        binding.editTextOnesPassword.editText?.setText(
            sharedPreferences.getString(
                "onesPassword",
                ""
            )
        )
    }

    private fun saveProductsSettings() {
        sharedPreferences.edit()
            .putString("onesAddress", binding.editTextOnesAddress.editText?.text.toString())
            .putString("onesUsername", binding.editTextOnesUsername.editText?.text.toString())
            .putString("onesPassword", binding.editTextOnesPassword.editText?.text.toString())
            .apply()
    }

    private fun syncData() {
        Toast.makeText(requireContext(), "TODO!", Toast.LENGTH_SHORT).show()
    }
}