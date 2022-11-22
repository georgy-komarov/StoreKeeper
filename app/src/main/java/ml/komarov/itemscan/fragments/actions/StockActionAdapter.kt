package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.StockActionUnitBinding

class StockActionAdapter : RecyclerView.Adapter<StockActionAdapter.StockActionViewHolder>() {

    private var UnitList = emptyList<StockActionUnit>()

    class StockActionViewHolder(
        val binding: StockActionUnitBinding
        ): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockActionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StockActionUnitBinding.inflate(inflater, parent, false)
        return StockActionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockActionViewHolder, position: Int) {
        holder.binding.stockPrice.text = UnitList[position].price.toString()
        holder.binding.stockAmount.text = UnitList[position].amount.toString()
        holder.binding.stockSize.text = UnitList[position].size
    }

    override fun getItemCount(): Int {
        return UnitList.size
    }

    fun setList(list: List<StockActionUnit>){
        UnitList = list
        notifyDataSetChanged()
    }

}