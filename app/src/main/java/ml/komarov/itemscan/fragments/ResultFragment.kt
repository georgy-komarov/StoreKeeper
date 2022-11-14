package ml.komarov.itemscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.FragmentResultBinding




abstract class ResultFragment : Fragment() {

    private var _binding: ViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private fun fillArgs() {
        val code = arguments?.getString("productId")
        binding.root.findViewById<TextView>(R.id.text_code).text = this::class.qualifiedName
        binding.root.findViewById<TextView>(R.id.text_fragment).text = code
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        fillArgs()
        return binding.root
    }
}