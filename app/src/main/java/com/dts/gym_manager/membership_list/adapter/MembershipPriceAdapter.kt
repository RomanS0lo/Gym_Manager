package com.dts.gym_manager.membership_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dts.gym_manager.R
import com.dts.gym_manager.membership_list.view_holder.MembershipViewHolder
import com.dts.gym_manager.model.Membership
import com.dts.gym_manager.model.Price
import java.util.Collections.list

class MembershipPriceAdapter : RecyclerView.Adapter<MembershipViewHolder>() {

    var onBuyClickListener: ((level: Membership.Level, duration: Int) -> Unit)? = null

    var items = listOf<Price>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembershipViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_membership_price, parent, false)
        return MembershipViewHolder(view)
    }

    override fun onBindViewHolder(holderMembership: MembershipViewHolder, position: Int) {
        holderMembership.bind(items[position], onBuyClickListener)
    }

    override fun getItemCount(): Int = items.size
}