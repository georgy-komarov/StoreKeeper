package ml.komarov.itemscan.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.databinding.FragmentPricesActionBinding

class PricesActionFragment : ResultFragment() {
    private lateinit var binding: FragmentPricesActionBinding

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewBinding {
        binding = FragmentPricesActionBinding.inflate(inflater, container, false)
        return binding
    }

    override fun setup() {
        super.setup()
        binding.textPrices.text = "prices_table"
    }
}