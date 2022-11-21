package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.StockUnitBinding

class StockActionAdapter : RecyclerView.Adapter<StockActionAdapter.StockActionViewHolder>() {

    private var UnitList = emptyList<StockActionUnit>()

    class StockActionViewHolder(
        val binding: StockUnitBinding
        ): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockActionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StockUnitBinding.inflate(inflater, parent, false)
        return StockActionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockActionViewHolder, position: Int) {
        holder.binding.price.text = UnitList[position].price.toString()
        holder.binding.amount.text = UnitList[position].amount.toString()
        holder.binding.size.text = UnitList[position].size
    }

    override fun getItemCount(): Int {
        return UnitList.size
    }

    fun setList(list: List<StockActionUnit>){
        UnitList = list
        notifyDataSetChanged()
    }

}