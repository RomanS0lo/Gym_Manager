package com.dts.gym_manager.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.schedule

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModel()
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListener()
    }

    private fun setupObservers() {
        viewModel.onResultLogin().observe(viewLifecycleOwner) { successLogin ->
            if (successLogin) {
                Snackbar.make(binding.btnNext, R.string.label_success, Snackbar.LENGTH_SHORT).show()
                handler.postDelayed(1000) {
                    findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToMainFragment())
                }
            } else {
                Snackbar.make(binding.btnNext, "Wrong email or password", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    @SuppressLint("SetTextI18n", "ShowToast")
    private fun setupListener() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToSignUpFragment())
        }
        binding.btnNext.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.login(email, password)
        }
    }
}
