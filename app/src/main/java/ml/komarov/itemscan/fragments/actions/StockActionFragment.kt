package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.databinding.FragmentStockActionBinding

class StockActionFragment : BaseResultFragment() {
    private lateinit var binding: FragmentStockActionBinding

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewBinding {
        binding = FragmentStockActionBinding.inflate(inflater, container, false)
        return binding
    }

    override fun setup() {
        super.setup()
        binding.textStock.text = "stock_table"
    }
}