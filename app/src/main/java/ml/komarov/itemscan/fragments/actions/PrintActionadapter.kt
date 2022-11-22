package ml.komarov.itemscan.fragments.actions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ml.komarov.itemscan.R
import ml.komarov.itemscan.databinding.PrintActionUnitBinding

class printActionAdapter : RecyclerView.Adapter<printActionAdapter.printActionViewHolder>() {

    private var UnitList = emptyList<PrintActionUnit>()

    class printActionViewHolder(
        val binding: PrintActionUnitBinding
    ): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): printActionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PrintActionUnitBinding.inflate(inflater, parent, false)
        return printActionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: printActionViewHolder, position: Int) {
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