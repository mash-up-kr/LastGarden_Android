package com.mashup.lastgarden.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mashup.base.autoCleared
import com.mashup.lastgarden.BuildConfig
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentSettingsBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.MeViewModel
import com.mashup.lastgarden.ui.onboarding.OnBoardingActivity

class SettingsFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSettingsBinding>()

    private val meViewModel by activityViewModels<MeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupViews(view: View) {
        setupToolbar()
        binding.versionName.text = BuildConfig.VERSION_NAME
        binding.logout.setOnClickListener {
            meViewModel.resetUser()
            startActivity(Intent(requireContext(), OnBoardingActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setupToolbar() {
        binding.toolbar.navigationIcon?.mutate()?.setTint(resources.getColor(R.color.point, null))
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }
}