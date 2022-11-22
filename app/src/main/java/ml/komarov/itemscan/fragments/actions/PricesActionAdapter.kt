package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ml.komarov.itemscan.databinding.PricesActionUnitBinding

class PricesActionAdapter : RecyclerView.Adapter<PricesActionAdapter.PricesActionViewHolder>() {

    private var unitList = emptyList<PricesActionUnit>()

    class PricesActionViewHolder(
        val binding: PricesActionUnitBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PricesActionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PricesActionUnitBinding.inflate(inflater, parent, false)
        return PricesActionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PricesActionViewHolder, position: Int) {
        holder.binding.pricesPrice.text = unitList[position].price.toString()
        holder.binding.pricesAmount.text = unitList[position].amount.toString()
        holder.binding.pricesSize.text = unitList[position].size
        holder.binding.pricesNewprice.text = "-"
    }

    override fun getItemCount(): Int {
        return unitList.size
    }

    fun setList(list: List<PricesActionUnit>) {
        unitList = list
        notifyDataSetChanged()
    }

}