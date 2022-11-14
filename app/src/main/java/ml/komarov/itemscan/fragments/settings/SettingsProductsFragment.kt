package ml.komarov.itemscan.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ml.komarov.itemscan.databinding.FragmentSettingsProductsBinding


class SettingsProductsFragment : Fragment() {

    private var _binding: FragmentSettingsProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsProductsBinding.inflate(inflater, container, false)

        setup()

        return binding.root
    }

    private fun setup() {
//        binding.btnScan.setOnClickListener {
//        }
//        binding.btnConfirm.setOnClickListener {
//        }
    }
}