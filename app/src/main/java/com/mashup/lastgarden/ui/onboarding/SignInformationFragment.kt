package com.mashup.lastgarden.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mashup.base.autoCleared
import com.mashup.lastgarden.databinding.FragmentSignInformationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInformationFragment : Fragment() {

    private var binding by autoCleared<FragmentSignInformationBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInformationBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
}