package com.mashup.lastgarden.ui.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mashup.base.autoCleared
import com.mashup.base.extensions.showToast
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentSignNameBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SignInNameFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSignNameBinding>()
    private val viewModel: SignViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignNameBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        initToolbar()

        binding.nameEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setUserName(text.toString())
        }

        binding.nextButton.setOnClickListener {
            viewModel.checkDuplicatedUserName()
        }
    }

    override fun onBindViewModelsOnViewCreated() {
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.nextButton.isEnabled = !name.isNullOrEmpty()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isValidName.collectLatest { isValidName ->
                if (isValidName) {
                    moveSignGenderFragment()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.snackBarStringResId.collectLatest { stringRes ->
                stringRes?.let {
                    showToast(stringRes)
                }
            }
        }
    }

    private fun initToolbar() {
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun moveSignGenderFragment() {
        findNavController().navigate(
            R.id.actionSignInInputNameFragmentToSignInputGenderFragment
        )
    }
}