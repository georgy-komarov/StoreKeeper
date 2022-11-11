package ml.komarov.itemscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import ml.komarov.itemscan.databinding.FragmentStockActionBinding

class StockActionFragment : ResultFragment() {
    private val args: StockActionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockActionBinding.inflate(inflater, container, false)
        fillArgs()
        return binding.root
    }
}