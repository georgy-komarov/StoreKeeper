package ml.komarov.itemscan.fragments.selectors

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import ml.komarov.itemscan.BarcodeActivity
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.FragmentMainBinding


abstract class BaseSelectorFragment : Fragment(), MenuProvider {

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
        setupMenu()

        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_settings -> {
                requireActivity().findNavController(R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_settings)
                true
            }
            else -> false
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    private fun setup() {
        binding.btnScan.setOnClickListener {
            val intent = Intent(activity, BarcodeActivity::class.java)
            resultLauncher.launch(intent)
        }
        binding.buttonConfirm.setOnClickListener {
            openCodeData(binding.editTextProduct.text.toString())
        }
    }

    private fun openCodeData(code: String) {
        productId = code
        navigateToAction()
    }

    abstract fun navigateToAction()
}