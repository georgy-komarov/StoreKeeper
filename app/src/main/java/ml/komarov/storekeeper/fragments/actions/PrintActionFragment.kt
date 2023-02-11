package ml.komarov.storekeeper.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ml.komarov.storekeeper.R
import ml.komarov.storekeeper.databinding.FragmentPrintActionBinding


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
        super.setup()

        val manager = LinearLayoutManager(context)
        recyclerView = binding.printRecycler
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)

        adapter = PrintActionAdapter()
        recyclerView.adapter = adapter

        adapter.setList(getProductDataAdapterList())
    }

    private fun getProductDataAdapterList(): ArrayList<PrintActionUnit> {
        val adapterList = ArrayList<PrintActionUnit>()
        for (productData in productDataList) {
            adapterList.add(
                PrintActionUnit(
                    false,
                    productData.sizeKey,
                    productData.size,
                    productData.price,
                    productData.amount,
                    "0"
                )
            )
        }
        return adapterList
    }

    override val menuCloseDestination = R.id.nav_print
}