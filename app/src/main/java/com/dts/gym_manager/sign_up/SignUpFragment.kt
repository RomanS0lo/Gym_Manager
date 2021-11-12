package com.dts.gym_manager.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.FragmentSignUpBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.bind(
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
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.onResultRegister().observe(viewLifecycleOwner) { successRegister ->
            if (successRegister) {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToMainFragment())
            } else {
                Snackbar.make(binding.root, R.string.message_cant_register, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.onUserSexUpdate().observe(viewLifecycleOwner) { sex ->
            binding.tvSex.text = sex
        }
    }

    private fun setupListeners() {
        val userSexNames = arrayOf(
            getString(R.string.label_male),
            getString(R.string.label_female)
        )
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.label_choose_sex)
            .setSingleChoiceItems(userSexNames, -1) { dialog, which ->
                viewModel.selectedSex(which, userSexNames[which])
                dialog.dismiss()
            }

        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToFragmentLogin())
        }

        binding.tvSex.setOnClickListener {
            dialog.show()
        }
        binding.btnRegistration.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val age = binding.etAge.text.toString().toInt()
            val sex = binding.tvSex.text.toString()
            val login = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.register(firstName, lastName, age, sex, login, password)
        }
    }
}
