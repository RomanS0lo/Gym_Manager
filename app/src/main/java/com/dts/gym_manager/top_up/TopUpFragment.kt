package com.dts.gym_manager.top_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.FragmentTopUpBinding
import com.dts.gym_manager.model.Wallets
import com.dts.gym_manager.top_up.adapter.TopUpViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class TopUpFragment : Fragment(R.layout.fragment_top_up), Communicator {

    private lateinit var binding: FragmentTopUpBinding

    private val viewModel: TopUpViewModel by viewModel()

    private var adapter: TopUpViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentTopUpBinding.bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupObservers()
        setupAdapter()
        setupListener()
        viewModel.loadWallets()
    }

    private fun setupData() {
        viewModel.onWalletResult().observe(viewLifecycleOwner) { wallets ->
            binding.tvHoursWallet.text = resources.getQuantityString(
                R.plurals.hourse_format,
                wallets.hours.roundToInt(),
                wallets.hours.roundToInt()
            )
            binding.tvMoneyWallet.text = resources.getString(R.string.money_format, wallets.money)
        }
    }

    private fun setupObservers() {
        viewModel.onTopUpResult().observe(viewLifecycleOwner) { wallets ->
            if (wallets.type == Wallets.ValueType.HOURS) {
                binding.tvHoursWallet.text = resources.getQuantityString(
                    R.plurals.hourse_format,
                    wallets.amount.roundToInt(),
                    wallets.amount.roundToInt()
                )
            } else {
                binding.tvMoneyWallet.text =
                    resources.getString(R.string.money_format, wallets.amount)
            }
        }
    }

    private fun setupListener() {
        binding.btnComplete.setOnClickListener {
            viewModel.topUp()
            adapter?.onCompleteTopUp()
        }
    }

    private fun setupAdapter() {
        val viewPager = binding.vpTopUp
        adapter = TopUpViewPagerAdapter(this, this)
        viewPager.adapter = adapter
        TabLayoutMediator(binding.tlTopUp, binding.vpTopUp) { tab, position ->
            tab.text = adapter?.getTitle(position)
        }.attach()
    }

    override fun saveTopUpValue(valueType: Wallets.ValueType, value: Int) {
        viewModel.saveTopUpValue(valueType, value.toFloat())
    }
}
