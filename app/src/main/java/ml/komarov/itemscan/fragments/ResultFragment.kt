package ml.komarov.itemscan.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_result.view.*
import ml.komarov.itemscan.App
import ml.komarov.itemscan.R
import ml.komarov.itemscan.db.AppDatabase
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ResultFragment : Fragment() {
    companion object {
        private val client = OkHttpClient()

        fun newInstance(): ResultFragment {
            val args = Bundle()

            val fragment = ResultFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var root: View? = null

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
        root = inflater.inflate(R.layout.fragment_result, container, false)

        requireActivity().title = getString(R.string.result)

        val args = requireArguments()
        val code = args.getString("code")

        root?.tvCode?.text = code

        return root
    }
}