package com.dts.gym_manager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dts.gym_manager.R
import com.dts.gym_manager.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

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
    }

    private fun setupData() {
        viewModel.onUserInfoResult().observe(viewLifecycleOwner) { userInfo ->
            binding.tvFirstName.text = resources.getString(R.string.label_first_name_format, userInfo.firstName.orEmpty())
            binding.tvLastName.text = resources.getString(R.string.label_last_name_format, userInfo.lastName.orEmpty())
            binding.tvAge.text = resources.getString(R.string.label_age_format, userInfo.age.toString())
        }
        viewModel.onMembershipResult().observe(viewLifecycleOwner) { membership ->
            binding.tvMembershipType.text = membership.level.name
        }
        viewModel.onFailResult().observe(viewLifecycleOwner) { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
        }
    }
}
