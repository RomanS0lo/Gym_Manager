package com.dts.gym_manager.top_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.MoneyTabBinding
import com.dts.gym_manager.model.Wallets
import timber.log.Timber


class MoneyTabFragment(private val communicator: Communicator) : Fragment(R.layout.money_tab),
    TopUpCallback {

    private lateinit var binding: MoneyTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            MoneyTabBinding.bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        binding.etTopUp.addTextChangedListener {
            if (binding.etTopUp.text?.isDigitsOnly() == true && !binding.etTopUp.text.isNullOrEmpty()) {
                communicator.saveTopUpValue(
                    Wallets.ValueType.MONEY,
                    binding.etTopUp.text.toString().toInt()
                )
            } else {
                Timber.d("Field is empty or not a digit")
            }
        }
    }

    override fun onCompleteTopUp() {
        clearInput()
    }

    private fun clearInput() {
        binding.etTopUp.setText("")
    }
}
