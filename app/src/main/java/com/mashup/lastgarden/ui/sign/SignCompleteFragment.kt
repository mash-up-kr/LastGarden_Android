package com.mashup.lastgarden.ui.sign

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.base.autoCleared
import com.mashup.lastgarden.databinding.FragmentSignCompleteBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.main.MainActivity

class SignCompleteFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSignCompleteBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignCompleteBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)

        binding.buttonNext.setOnClickListener {
            moveMainActivity()
        }
    }

    private fun moveMainActivity() {
        requireActivity().run {
            startActivity(
                Intent(requireContext(), MainActivity::class.java)
            )
            finish()
        }
    }
}