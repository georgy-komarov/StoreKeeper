package ml.komarov.itemscan.fragments.selectors

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ml.komarov.itemscan.BarcodeActivity
import ml.komarov.itemscan.databinding.FragmentMainBinding


abstract class BaseSelectorFragment : Fragment() {

    lateinit var productId: String

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val code = data?.getStringExtra("DATA")!!
                openCodeData(code)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        setup()

        return binding.root
    }

    private fun setup () {
        binding.btnScan.setOnClickListener {
            val intent = Intent(activity, BarcodeActivity::class.java)
            resultLauncher.launch(intent)
        }
        binding.btnConfirm.setOnClickListener {
            openCodeData(binding.editTextMark.text.toString())
        }
    }

    private fun openCodeData(code: String) {
        productId = code
        navigateToAction()
    }

    abstract fun navigateToAction()
}