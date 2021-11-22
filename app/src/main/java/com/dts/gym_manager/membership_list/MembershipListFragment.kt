package com.dts.gym_manager.membership_list

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dts.gym_manager.R
import com.dts.gym_manager.data.PrefsRepositoryImpl
import com.dts.gym_manager.databinding.FragmentMembershipListBinding
import com.dts.gym_manager.membership_list.adapter.MembershipPriceAdapter
import com.dts.gym_manager.model.Membership
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("NotifyDataSetChanged")
class MembershipListFragment : Fragment(R.layout.fragment_membership_list) {

    private lateinit var binding: FragmentMembershipListBinding
    private val viewModel: MembershipListViewModel by viewModel()
    private val adapter = MembershipPriceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMembershipListBinding.bind(
            super.onCreateView(
                inflater,
                container,
                savedInstanceState
            )!!
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMembershipPrice.adapter = adapter
        viewModel.setupMembershipPriceList()
        adapter.onBuyClickListener = ::onClickBuy
        setupData()

    }

    private fun onClickBuy(level: Membership.Level, duration: Int) {
            viewModel.createMembership(level, duration)
            viewModel.setupHomeData()
            upgradeMembership(level)
        }

    private fun upgradeMembership(level: Membership.Level){
        viewModel.onMembershipResult().observe(viewLifecycleOwner) { membership ->
            if (level != membership?.level){
                viewModel.onShowUpgradeDialog = {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(getString(R.string.label_attention))
                        .setMessage(R.string.label_upgrade_membership_dialog)
                        .setPositiveButton(getString(R.string.label_accept)) { dialog, which ->
                            membership?.let { viewModel.upgradeMembership(it.userId, level) }
                        }
                        .setNegativeButton(getString(R.string.label_decline)){ dialog, which ->
                            dialog.dismiss()
                        }.show()
                }
            }
        }
    }

    private fun setupData() {
        viewModel.onMembershipPriceResult().observe(viewLifecycleOwner) { prices ->
            adapter.items = prices
        }
        viewModel.onCreateMembershipResult().observe(viewLifecycleOwner) {
            binding.root.let {
                Snackbar.make(
                    it,
                    getString(R.string.label_membership_was_created),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.onFailResult().observe(viewLifecycleOwner) { message ->
            binding.root.let {
                Snackbar.make(
                    it,
                    message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}
