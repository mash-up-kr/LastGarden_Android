package com.mashup.lastgarden.ui.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentSignGenderBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInGenderFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSignGenderBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignGenderBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        initToolbar()
        setUiOfCheckBox()

        binding.nextButton.setOnClickListener {
            moveSignCompleteFragment()
        }
    }

    private fun initToolbar() {
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun setUiOfCheckBox() = with(binding) {
        binding.femaleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.maleCheckBox.isChecked = false
                binding.unknownCheckBox.isChecked = false
            }
        }
        binding.maleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.femaleCheckBox.isChecked = false
                binding.unknownCheckBox.isChecked = false
            }
        }
        binding.unknownCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.maleCheckBox.isChecked = false
                binding.femaleCheckBox.isChecked = false
            }
        }
    }

    private fun moveSignCompleteFragment() {
        findNavController().navigate(
            R.id.actionSignInputGenderFragmentToSignCompleteFragment
        )
    }
}