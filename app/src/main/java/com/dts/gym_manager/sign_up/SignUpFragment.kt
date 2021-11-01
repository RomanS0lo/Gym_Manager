package com.dts.gym_manager.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.FragmentSignUpBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment: Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding

    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
