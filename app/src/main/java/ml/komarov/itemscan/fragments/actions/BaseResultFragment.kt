package ml.komarov.itemscan.fragments.actions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.databinding.FragmentResultBinding


abstract class BaseResultFragment : Fragment() {

    private lateinit var _resultBinding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Make sure to create the concrete binding while it's required to
        // create the resultBinding from it
        val binding = onCreateViewBinding(inflater, container)

        // We're using the concrete layout of the child class to create our
        // commonly used binding
        _resultBinding = FragmentResultBinding.bind(binding.root)

        setup()

        return binding.root
    }

    // Makes sure to create the concrete binding class from child-classes before
    // the commonBinding can be bound
    protected abstract fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewBinding

    // Allows child-classes to access the resultBinding to access common
    // used views
    private val resultBinding get() = _resultBinding

    protected open fun setup() {
        val code = arguments?.getString("productId")
        resultBinding.textCode.text = code
        resultBinding.textProduct.text = "товар"
        resultBinding.textSku.text = "артикул"
        resultBinding.textSize.text = "размер"
    }
}