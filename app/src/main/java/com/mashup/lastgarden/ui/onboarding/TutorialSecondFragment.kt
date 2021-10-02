package com.mashup.lastgarden.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentTutorialFirstBinding
import com.mashup.lastgarden.databinding.FragmentTutorialSecondBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TutorialSecondFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentTutorialSecondBinding>()

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorialSecondBinding.inflate(
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
            R.drawable.img_tutorial_second
        )
    }
}