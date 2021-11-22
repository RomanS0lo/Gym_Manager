package com.dts.gym_manager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentHomeBinding.bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        viewModel.setupHomeData()
        setupListener()
    }

    private fun setupListener() {
        binding.btnTopUp.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionMainFragmentToTopUpFragment())
        }
        binding.fabShop.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionMainFragmentToPacketFragment())
        }
        binding.fabBuyMembership.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionMainFragmentToMembershipListFragment())
        }
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
        viewModel.onUserInfoResult().observe(viewLifecycleOwner) { userInfo ->
            binding.tvFirstName.text =
                resources.getString(R.string.label_first_name_format, userInfo.firstName.orEmpty())
            binding.tvLastName.text =
                resources.getString(R.string.label_last_name_format, userInfo.lastName.orEmpty())
            binding.tvAge.text =
                resources.getString(R.string.label_age_format, userInfo.age.toString())
        }
        viewModel.onMembershipResult().observe(viewLifecycleOwner) { membership ->
            binding.tvMembershipType.text = membership?.level?.name
        }
        viewModel.onFailResult().observe(viewLifecycleOwner) { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
        }
    }
}
