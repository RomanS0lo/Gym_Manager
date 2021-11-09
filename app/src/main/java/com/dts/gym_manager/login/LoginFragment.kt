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
import timber.log.Timber

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModel()
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.isUserLogged()) {
            navigateToHome()
        } else {
            Timber.d("User is not logged in")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentLoginBinding.bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListener()
    }

    private fun navigateToHome() =
        findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToMainFragment())

    private fun setupObservers() {
        viewModel.onResultLogin().observe(viewLifecycleOwner) { successLogin ->
            if (successLogin) {
                Snackbar.make(binding.root, R.string.label_success, Snackbar.LENGTH_SHORT).show()
                handler.postDelayed(1000) {
                    navigateToHome()
                }
            } else {
                Snackbar.make(binding.root, R.string.label_wrong_email, Snackbar.LENGTH_SHORT)
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
