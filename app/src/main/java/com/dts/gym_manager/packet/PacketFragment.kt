package com.dts.gym_manager.packet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.CartBottomSheetBinding
import com.dts.gym_manager.databinding.FragmentMyPacketBinding
import com.dts.gym_manager.packet.adapter.CartAdapter
import com.dts.gym_manager.packet.adapter.PacketAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class PacketFragment : Fragment(R.layout.fragment_my_packet) {

    private lateinit var binding: FragmentMyPacketBinding

    private val viewModel: PacketViewModel by viewModel()

    private val adapter by lazy { PacketAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPacketBinding.bind(
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
        setupAdapter()
        setupObserver()
        setupListener()
        viewModel.getGoodsList()
    }

    private fun setupListener() {
        binding.btnShowCart.setOnClickListener {
            if (viewModel.getCartGoods().isEmpty()) {
                Snackbar.make(binding.root, R.string.message_cart_empty, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                val dialog = BottomSheetDialog(requireContext())
                val cartBinding =
                    CartBottomSheetBinding.inflate(LayoutInflater.from(requireContext()))
                cartBinding.rvCart.adapter = CartAdapter(viewModel.getCartGoods())
                dialog.setContentView(cartBinding.root)
                dialog.show()
                cartBinding.btnBuy.setOnClickListener {
                    viewModel.purchaseGoods()
                    dialog.dismiss()
                }
            }
        }
    }

    private fun setupObserver() {
        viewModel.onGoodsResult().observe(viewLifecycleOwner) { goods ->
            adapter.items = goods
        }
        viewModel.onPurchaseSuccessResult().observe(viewLifecycleOwner) { answer ->
            if (answer) {
                Snackbar.make(
                    binding.root,
                    R.string.label_purchase_successful,
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(binding.root, R.string.label_purchase_failed, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupAdapter() {
        binding.rvGoods.adapter = adapter

        adapter.onClickListener = { goods ->
            val dialog = PurchaseConfirmationDialog(goods)
            dialog.onConfirmCallback = {
                viewModel.addGoods(goods)
            }
            dialog.show(
                childFragmentManager, PurchaseConfirmationDialog.TAG
            )
        }
    }
}
