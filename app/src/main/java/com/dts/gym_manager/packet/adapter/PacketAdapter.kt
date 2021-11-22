package com.dts.gym_manager.packet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.GoodCellBinding
import com.dts.gym_manager.model.Goods
import com.dts.gym_manager.model.Wallets
import kotlin.math.roundToInt

class PacketAdapter : RecyclerView.Adapter<PacketAdapter.PacketViewHolder>() {

    inner class PacketViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = GoodCellBinding.bind(view)

        fun bind(view: Goods, clickListener: ((Goods) -> Unit)?) {
            binding.tvTitle.text = view.title
            binding.tvDescription.text = view.description
            if (view.currencyType == Wallets.ValueType.HOURS) {
                binding.tvPrice.text = itemView.resources.getQuantityString(
                    R.plurals.hourse_format,
                    view.price.roundToInt(),
                    view.price.roundToInt()
                )
            } else {
                binding.tvPrice.text = itemView.resources.getString(R.string.money_format, view.price)
            }
            binding.btnBuy.setOnClickListener {
                clickListener?.invoke(view)
            }
        }
    }

    var items = listOf<Goods>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.good_cell, parent, false)
        return PacketViewHolder(view)
    }

    override fun onBindViewHolder(holder: PacketViewHolder, position: Int) {
        holder.bind(items[position], onClickListener)
    }

    override fun getItemCount(): Int = items.size

    var onClickListener: ((Goods) -> Unit)? = null
}
