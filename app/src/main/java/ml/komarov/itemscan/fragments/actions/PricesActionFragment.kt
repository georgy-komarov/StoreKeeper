package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.FragmentPricesActionBinding


class PricesActionFragment : BaseResultFragment() {
    private lateinit var binding: FragmentPricesActionBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PricesActionAdapter

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewBinding {
        binding = FragmentPricesActionBinding.inflate(inflater, container, false)
        return binding
    }

    override fun setup() {
        super.setup()

        val manager = LinearLayoutManager(context)
        recyclerView = binding.pricesRecycler
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)

        adapter = PricesActionAdapter()
        recyclerView.adapter = adapter

        adapter.setList(getProductDataAdapterList())
    }

    private fun getProductDataAdapterList(): ArrayList<PricesActionUnit> {
        val adapterList = ArrayList<PricesActionUnit>()
        for (productData in productDataList) {
            adapterList.add(
                PricesActionUnit(
                    false,
                    productData.sizeKey,
                    productData.size,
                    productData.price,
                    productData.amount,
                    "-"
                )
            )
        }
        return adapterList
    }

    override val menuCloseDestination = R.id.nav_prices
}