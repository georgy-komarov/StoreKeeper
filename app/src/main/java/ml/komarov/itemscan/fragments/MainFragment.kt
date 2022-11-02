package ml.komarov.itemscan.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import ml.komarov.itemscan.BarcodeActivity
import ml.komarov.itemscan.R


class MainFragment : Fragment() {
    companion object {
        fun newInstance(): MainFragment {
            val args = Bundle()

            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val code = data?.getStringExtra("DATA")!!
            openCodeData(code)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        requireActivity().title = getString(R.string.app_name)

        view.btnScan.setOnClickListener {
            val intent = Intent(activity, BarcodeActivity::class.java)
            resultLauncher.launch(intent)
        }
        view.btnConfirm.setOnClickListener {
            openCodeData(editTextMark.text.toString())
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(activity, item.title, Toast.LENGTH_SHORT).show()

        when (item.itemId) {
//            R.id.menuSettings ->
            R.id.menuExit -> requireActivity().finishAndRemoveTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openCodeData(code: String) {
        val args = Bundle()
        args.putString("code", code)

        val newResultFragment = ResultFragment.newInstance()
        newResultFragment.arguments = args

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, newResultFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(ResultFragment::class.qualifiedName)
            .commit()
    }
}