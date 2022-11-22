package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ml.komarov.itemscan.databinding.PrintActionUnitBinding

class PrintActionAdapter : RecyclerView.Adapter<PrintActionAdapter.PrintActionViewHolder>() {

    private var unitList = emptyList<PrintActionUnit>()

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
        holder.binding.printPrice.text = unitList[position].price.toString()
        holder.binding.printAmount.text = unitList[position].amount.toString()
        holder.binding.printSize.text = unitList[position].size

    }

    override fun getItemCount(): Int {
        return unitList.size
    }

    fun setList(list: List<PrintActionUnit>){
        unitList = list
        notifyDataSetChanged()
    }

}