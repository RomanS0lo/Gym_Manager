package com.dts.gym_manager.packet

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.dts.gym_manager.R
import com.dts.gym_manager.model.Goods

class PurchaseConfirmationDialog(private val goods: Goods) : DialogFragment() {

    var onConfirmCallback: ((Goods) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(goods.title)
            .setMessage(getString(R.string.dialog_add_to_card))
            .setPositiveButton(
                getString(R.string.label_yes)
            ) { _, _ ->
                onConfirmCallback?.invoke(goods)
                dialog?.cancel()
            }
            .setNegativeButton(getString(R.string.label_no)) { _, _ ->
                dialog?.cancel()
            }
            .create()

    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }
}
