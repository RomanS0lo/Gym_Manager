package com.dts.gym_manager.membership_list.view_holder

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.ItemMembershipPriceBinding
import com.dts.gym_manager.membership_list.MembershipListFragment
import com.dts.gym_manager.membership_list.MembershipListViewModel
import com.dts.gym_manager.model.Membership
import com.dts.gym_manager.model.Price
import org.koin.androidx.viewmodel.ext.android.viewModel

class MembershipViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ItemMembershipPriceBinding = ItemMembershipPriceBinding.bind(view)

    fun bind(price: Price, itemClick: ((level: Membership.Level, duration: Int) -> Unit)?) {
        binding.tvPrice.text = itemView.resources.getString(R.string.label_price_format, price.price)
        binding.tvMembershipDuration.text = itemView.resources.getString(R.string.label_month_duration_format, price.duration)
        binding.cvMembership.setCardBackgroundColor(obtainBackColor(price.level))
        binding.btnBuy.backgroundTintList = ColorStateList.valueOf(setBtnColor(price.level))
        binding.tvPrice.setTextColor(setBtnColor(price.level))
        binding.tvLevel.setTextColor(setBtnColor(price.level))
        binding.tvMembershipDuration.setTextColor(setBtnColor(price.level))


        binding.tvLevel.text = price.level.toString()

        binding.btnBuy.setOnClickListener {
            if (itemClick != null) {
                itemClick(price.level, price.duration)
                binding.btnBuy.isEnabled = false
            }
        }
    }

    private fun setBtnColor(level: Membership.Level): Int = when (level) {
        Membership.Level.BASIC -> ContextCompat.getColor(itemView.context, R.color.orange2)
        Membership.Level.GOLD -> ContextCompat.getColor(itemView.context, R.color.blue2)
        Membership.Level.PREMIUM -> ContextCompat.getColor(itemView.context, R.color.purple2)
    }

    private fun obtainBackColor(level: Membership.Level): Int = when (level) {
        Membership.Level.BASIC -> ContextCompat.getColor(itemView.context, R.color.orange)
        Membership.Level.GOLD -> ContextCompat.getColor(itemView.context, R.color.blue)
        Membership.Level.PREMIUM -> ContextCompat.getColor(itemView.context, R.color.purple)
    }
}
