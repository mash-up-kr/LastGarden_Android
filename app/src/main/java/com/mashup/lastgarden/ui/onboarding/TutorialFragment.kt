package com.mashup.lastgarden.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentTutorialBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TutorialFragment(private val page: Int) : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentTutorialBinding>()

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorialBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        setupTutorialImage(page)
        setupTutorialTitle(page)
        setupTutorialDescription(page)

    }

    private fun setupTutorialImage(page: Int) {
        when (page) {
            0 -> binding.tutorialImage.loadImage(
                glideRequests,
                R.drawable.img_tutorial_first
            )
            1 -> binding.tutorialImage.loadImage(
                glideRequests,
                R.drawable.img_tutorial_second
            )
            2 -> binding.tutorialImage.loadImage(
                glideRequests,
                R.drawable.img_tutorial_third
            )
        }

    }

    private fun setupTutorialTitle(page: Int) {
        when (page) {
            0 -> binding.title.text = getString(R.string.tutorial_first_title)
            1 -> binding.title.text = getString(R.string.tutorial_second_title)
            else -> binding.title.text = getString(R.string.tutorial_third_title)
        }
    }

    private fun setupTutorialDescription(page: Int) {
        when (page) {
            0 -> binding.description.text = getString(R.string.tutorial_first_description)
            1 -> binding.description.text = getString(R.string.tutorial_second_description)
            else -> binding.description.text = getString(R.string.tutorial_third_description)
        }
    }
}