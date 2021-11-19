package com.dts.gym_manager.top_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.HoursTabBinding
import com.dts.gym_manager.model.HoursItemPrice
import com.dts.gym_manager.model.Wallets
import com.dts.gym_manager.top_up.adapter.TopUpRecyclerAdapter

class HoursTabFragment(private val communicator: Communicator) : Fragment(R.layout.hours_tab), TopUpCallback {

    private lateinit var binding: HoursTabBinding
    private val adapter = TopUpRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            HoursTabBinding.bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.rvHourPrices.adapter = adapter
        adapter.onItemCLick = { hourPrice ->
            communicator.saveTopUpValue(Wallets.ValueType.HOURS, hourPrice.duration)
        }
        adapter.items.add(HoursItemPrice(30f, 1))
        adapter.items.add(HoursItemPrice(75f, 3))
        adapter.items.add(HoursItemPrice(280f, 10))
    }

    override fun onCompleteTopUp() {
        adapter.unselectedAll()
    }
}
