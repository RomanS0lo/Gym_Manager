package com.dts.gym_manager.top_up.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.HourPriceCellBinding
import com.dts.gym_manager.model.HoursItemPrice

class TopUpRecyclerAdapter : RecyclerView.Adapter<TopUpRecyclerAdapter.TopUpViewHolder>() {

    inner class TopUpViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = HourPriceCellBinding.bind(view)

        fun bind(data: HoursItemPrice, itemClick: ((HoursItemPrice) -> Unit)?) {
            binding.cv1Hour.isSelected = data.isSelected

            binding.root.setOnClickListener {
                itemClick?.invoke(data)
            }
            binding.tvLabel1Hour.text = itemView.resources.getQuantityString(
                R.plurals.hourse_format,
                data.duration,
                data.duration
            )
            binding.tvLabel30USD.text =
                itemView.resources.getString(R.string.money_format, data.value)
        }
    }

    var items = mutableListOf<HoursItemPrice>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUpViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.hour_price_cell, parent, false)
        return TopUpViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopUpViewHolder, position: Int) {
        holder.bind(items[position]) { item ->
            items.forEach { it.isSelected = false }
            item.isSelected = true
            notifyDataSetChanged()
            onItemCLick?.invoke(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun unselectedAll() {
        items.forEach { it.isSelected = false }
        notifyDataSetChanged()
    }

    var onItemCLick: ((HoursItemPrice) -> Unit)? = null

}
