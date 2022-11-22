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
        recyclerView = binding.pricesRecycler
        val manager = LinearLayoutManager(context)
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)
        adapter = PricesActionAdapter()
        recyclerView.adapter = adapter
        adapter.setList(testData())

        super.setup()
    }

    private fun testData(): ArrayList<PricesActionUnit>{
        val finalList = ArrayList<PricesActionUnit>()
        finalList.add(PricesActionUnit(false, "42", 10, 1, "-"))
        finalList.add(PricesActionUnit(false, "52", 20, 1, "-"))
        finalList.add(PricesActionUnit(false, "32", 10, 1, "-"))
        finalList.add(PricesActionUnit(false, "42", 10, 1, "-"))


        return finalList
    }

    override val menuCloseDestination = R.id.nav_prices
}