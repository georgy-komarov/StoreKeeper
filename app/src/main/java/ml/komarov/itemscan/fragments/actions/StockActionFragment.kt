package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.FragmentStockActionBinding


class StockActionFragment : BaseResultFragment() {
    private lateinit var binding: FragmentStockActionBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StockActionAdapter

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewBinding {
        binding = FragmentStockActionBinding.inflate(inflater, container, false)
        return binding
    }

    override fun setup() {
        recyclerView = binding.stockRecycler
        val manager = LinearLayoutManager(context)
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)
        adapter = StockActionAdapter()
        recyclerView.adapter = adapter
        adapter.setList(testData())

        super.setup()

    }

    private fun testData(): ArrayList<StockActionUnit>{
        val finalList = ArrayList<StockActionUnit>()
        finalList.add(StockActionUnit("42", 10, 1))
        finalList.add(StockActionUnit("54", 20, 2))
        finalList.add(StockActionUnit("21", 42, 5))
        finalList.add(StockActionUnit("21", 42, 5))
        finalList.add(StockActionUnit("21", 42, 2))
        finalList.add(StockActionUnit("21", 42, 5))
        finalList.add(StockActionUnit("21", 42, 5))
        finalList.add(StockActionUnit("21", 42, 5))
        finalList.add(StockActionUnit("21", 42, 5))
        finalList.add(StockActionUnit("21", 42, 5))
        return finalList
    }

    override val menuCloseDestination = R.id.nav_stock
}
