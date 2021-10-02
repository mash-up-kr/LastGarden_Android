package com.mashup.lastgarden.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentTutorialThirdBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TutorialThirdFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentTutorialThirdBinding>()

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorialThirdBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        setupTutorialImage()
    }

    private fun setupTutorialImage() {
        binding.tutorialImage.loadImage(
            glideRequests,
            R.drawable.img_tutorial_third
        )
    }
}