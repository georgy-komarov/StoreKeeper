package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.FragmentPrintActionBinding


class PrintActionFragment : BaseResultFragment() {
    private lateinit var binding: FragmentPrintActionBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PrintActionAdapter

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewBinding {
        binding = FragmentPrintActionBinding.inflate(inflater, container, false)
        return binding
    }

    override fun setup() {
        recyclerView = binding.printRecycler
        val manager = LinearLayoutManager(context)
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)
        adapter = PrintActionAdapter()
        recyclerView.adapter = adapter
        adapter.setList(testData())

        super.setup()
    }

    private fun testData(): ArrayList<PrintActionUnit>{
        val finalList = ArrayList<PrintActionUnit>()
        finalList.add(PrintActionUnit(false, "42", 10, 1, "0"))
        finalList.add(PrintActionUnit(false, "52", 20, 5, "0"))
        finalList.add(PrintActionUnit(false, "32", 30, 7, "0"))
        finalList.add(PrintActionUnit(false, "42", 10, 1, "0"))


        return finalList
    }

    override val menuCloseDestination = R.id.nav_print
}