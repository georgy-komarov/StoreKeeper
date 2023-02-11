package ml.komarov.storekeeper.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ml.komarov.storekeeper.databinding.StockActionUnitBinding

class StockActionAdapter : RecyclerView.Adapter<StockActionAdapter.StockActionViewHolder>() {

    private var unitList = emptyList<StockActionUnit>()

    class StockActionViewHolder(
        val binding: StockActionUnitBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockActionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StockActionUnitBinding.inflate(inflater, parent, false)
        return StockActionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockActionViewHolder, position: Int) {
        holder.binding.stockPrice.text = unitList[position].price.toString()
        holder.binding.stockAmount.text = unitList[position].amount.toString()
        holder.binding.stockSize.text = unitList[position].size
    }

    override fun getItemCount(): Int {
        return unitList.size
    }

    fun setList(list: List<StockActionUnit>) {
        unitList = list
        notifyDataSetChanged()
    }

}