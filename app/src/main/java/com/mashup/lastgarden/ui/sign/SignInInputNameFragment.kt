package com.mashup.lastgarden.ui.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentSignNameBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInInputNameFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSignNameBinding>()

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
        binding.editTextName.doOnTextChanged { text, _, _, _ ->
            binding.buttonNext.isEnabled = !text.isNullOrEmpty()
        }

        binding.buttonNext.setOnClickListener {
            moveSignCompleteFragment()
        }
    }


    private fun moveSignCompleteFragment() {
        findNavController().navigate(
            R.id.actionSignInInputNameFragmentToSignInputGenderFragment
        )
    }
}