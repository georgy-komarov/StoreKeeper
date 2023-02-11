package ml.komarov.storekeeper.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ml.komarov.storekeeper.R
import ml.komarov.storekeeper.databinding.FragmentStockActionBinding


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
        super.setup()

        val manager = LinearLayoutManager(context)
        recyclerView = binding.stockRecycler
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)

        adapter = StockActionAdapter()
        recyclerView.adapter = adapter

        adapter.setList(getProductDataAdapterList())
    }

    private fun getProductDataAdapterList(): ArrayList<StockActionUnit> {
        val adapterList = ArrayList<StockActionUnit>()
        for (productData in productDataList) {
            adapterList.add(
                StockActionUnit(
                    productData.sizeKey,
                    productData.size,
                    productData.price,
                    productData.amount,
                )
            )
        }
        return adapterList
    }

    override val menuCloseDestination = R.id.nav_stock
}
