package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ml.komarov.itemscan.databinding.PrintActionUnitBinding

class PrintActionAdapter : RecyclerView.Adapter<PrintActionAdapter.PrintActionViewHolder>() {

    private var UnitList = emptyList<PrintActionUnit>()

    class PrintActionViewHolder(
        val binding: PrintActionUnitBinding
    ): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrintActionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PrintActionUnitBinding.inflate(inflater, parent, false)
        return PrintActionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrintActionViewHolder, position: Int) {
        holder.binding.printPrice.text = UnitList[position].price.toString()
        holder.binding.printAmount.text = UnitList[position].amount.toString()
        holder.binding.printSize.text = UnitList[position].size

    }

    override fun getItemCount(): Int {
        return UnitList.size
    }

    fun setList(list: List<PrintActionUnit>){
        UnitList = list
        notifyDataSetChanged()
    }

}