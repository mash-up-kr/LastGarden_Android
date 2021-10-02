package com.mashup.lastgarden.ui.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mashup.base.autoCleared
import com.mashup.base.extensions.underLine
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentSignInBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSignInBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        binding.unUsedSign.underLine()

        binding.loginGoogleButton.setOnClickListener {
            moveSignInformationFragment()
        }
    }

    private fun moveSignInformationFragment() {
        findNavController().navigate(
            R.id.actionSignInFragmentToSignInInputNameFragment
        )
    }
}