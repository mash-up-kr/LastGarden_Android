package com.mashup.lastgarden.ui.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentSignGenderBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInInputGenderFragment : BaseViewModelFragment() {

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
        setUiOfCheckBox()

        binding.buttonNext.setOnClickListener {
            moveSignCompleteFragment()
        }
    }

    private fun setUiOfCheckBox() = with(binding) {
        binding.checkBoxFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxMale.isChecked = false
            }
        }
        binding.checkBoxMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxFemale.isChecked = false
            }
        }
        binding.checkBoxUnknown.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxMale.isChecked = false
                binding.checkBoxFemale.isChecked = false
            }
        }
    }

    private fun moveSignCompleteFragment() {
        findNavController().navigate(
            R.id.actionSignInputGenderFragmentToSignCompleteFragment
        )
    }
}