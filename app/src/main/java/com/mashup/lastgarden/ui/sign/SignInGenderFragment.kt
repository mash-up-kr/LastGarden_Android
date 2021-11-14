package com.mashup.lastgarden.ui.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentSignGenderBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SignInGenderFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSignGenderBinding>()
    private val viewModel: SignViewModel by activityViewModels()

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

        binding.ageEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setUserAgeByString(text.toString())
        }
    }

    override fun onBindViewModelsOnViewCreated() {
        viewModel.genderType.observe(viewLifecycleOwner) { genderType ->
            binding.femaleCheckBox.isChecked = genderType == GenderType.FEMALE
            binding.maleCheckBox.isChecked = genderType == GenderType.MALE
            binding.unknownCheckBox.isChecked = genderType == GenderType.UNKNOWN
        }

        viewModel.createdUser.observe(viewLifecycleOwner) {
            moveSignCompleteFragment()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isValidUserInformation.collectLatest { isValidUserInfo ->
                binding.nextButton.isEnabled = isValidUserInfo
            }
        }
    }

    private fun initToolbar() {
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun setUiOfCheckBox() = with(binding) {
        binding.femaleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.setGenderType(GenderType.FEMALE)
            }
        }
        binding.maleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.setGenderType(GenderType.MALE)
            }
        }
        binding.unknownCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.setGenderType(GenderType.UNKNOWN)
            }
        }
    }

    private fun moveSignCompleteFragment() {
        findNavController().navigate(
            R.id.actionSignInputGenderFragmentToSignCompleteFragment
        )
    }
}