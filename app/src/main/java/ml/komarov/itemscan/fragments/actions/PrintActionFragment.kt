package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.databinding.FragmentPrintActionBinding


class PrintActionFragment : BaseResultFragment() {

    private lateinit var binding: FragmentPrintActionBinding

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewBinding {
        binding = FragmentPrintActionBinding.inflate(inflater, container, false)
        return binding
    }

    override fun setup() {
        super.setup()
        binding.textPrint.text = "print_table"
    }
}