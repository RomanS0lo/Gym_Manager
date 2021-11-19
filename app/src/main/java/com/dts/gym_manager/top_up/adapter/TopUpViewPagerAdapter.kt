package com.dts.gym_manager.top_up.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dts.gym_manager.R
import com.dts.gym_manager.top_up.Communicator
import com.dts.gym_manager.top_up.HoursTabFragment
import com.dts.gym_manager.top_up.MoneyTabFragment
import com.dts.gym_manager.top_up.TopUpCallback


class TopUpViewPagerAdapter(
    private val fragment: Fragment,
    communicator: Communicator
) : FragmentStateAdapter(fragment) {

    private val tabs = arrayListOf<Fragment>()

    init {
        tabs.add(MoneyTabFragment(communicator))
        tabs.add(HoursTabFragment(communicator))
    }

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        return tabs[position]
    }

    fun getTitle(position: Int): CharSequence? {
        return if (position == 0) fragment.getString(R.string.label_money)
        else fragment.getString(R.string.label_hours)
    }

    fun onCompleteTopUp() {
        tabs.forEach { (it as TopUpCallback).onCompleteTopUp() }
    }
}
