package ml.komarov.itemscan.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ml.komarov.itemscan.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        setup()

        return binding.root
    }

    private fun setup() {
        binding.settingsProductsButton.setOnClickListener {
            val action = SettingsFragmentDirections.actionNavSettingsToNavSettingsProducts()
            view?.findNavController()?.navigate(action)
        }
        binding.settingsPrinterButton.setOnClickListener {
            val action = SettingsFragmentDirections.actionNavSettingsToNavSettingsPrinter()
            view?.findNavController()?.navigate(action)
        }
    }
}