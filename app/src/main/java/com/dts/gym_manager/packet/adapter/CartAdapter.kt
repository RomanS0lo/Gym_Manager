package com.dts.gym_manager.packet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.CartCellBinding
import com.dts.gym_manager.model.Goods

class CartAdapter(private val items: List<Goods>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private  val binding = CartCellBinding.bind(view)

        fun bind(view: Goods) {
            binding.tvName.text = view.title
            binding.tvPrice.text = view.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cart_cell, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}