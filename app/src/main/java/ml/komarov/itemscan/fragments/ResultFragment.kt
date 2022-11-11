package ml.komarov.itemscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.databinding.FragmentResultBinding

interface FragmentResultBindingAbstract {
    val textFragment: TextView
    val textCode: TextView
}

abstract class ResultFragment : Fragment() {

    protected var _binding: ViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    protected fun fillArgs() {
        val code = arguments?.getString("productId")
        val resultBinding = binding as FragmentResultBindingAbstract
        resultBinding.textFragment.text = this::class.qualifiedName
        resultBinding.textCode.text = code
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