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
    private lateinit var adapter: printActionAdapter

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
        adapter = printActionAdapter()
        recyclerView.adapter = adapter
        adapter.setList(testData())

        super.setup()
    }

    private fun testData(): ArrayList<PrintActionUnit>{
        val finalList = ArrayList<PrintActionUnit>()
        finalList.add(PrintActionUnit("42", 10, 1, 0))
        finalList.add(PrintActionUnit("52", 20, 2, 0))
        finalList.add(PrintActionUnit("32", 30, 3, 0))
        finalList.add(PrintActionUnit("52", 20, 2, 0))
        finalList.add(PrintActionUnit("52", 20, 2, 0))
        finalList.add(PrintActionUnit("52", 20, 2, 0))
        finalList.add(PrintActionUnit("52", 20, 2, 0))

        return finalList
    }

    override val menuCloseDestination = R.id.nav_print
}