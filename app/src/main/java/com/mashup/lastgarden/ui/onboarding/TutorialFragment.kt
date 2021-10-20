package com.mashup.lastgarden.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentTutorialBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TutorialFragment : BaseViewModelFragment() {

    companion object {
        private const val PAGE_TYPE = "page_type"
        fun createInstance(page: Int) = TutorialFragment().apply {
            arguments = Bundle().apply {
                putInt(PAGE_TYPE, page)
            }
        }
    }

    enum class OnBoardingType(
        @StringRes val titleRes: Int,
        @StringRes val descRes: Int,
        @DrawableRes val imageRes: Int
    ) {
        SEE_HYANG(
            R.string.tutorial_seehyang_title,
            R.string.tutorial_seehyang_description,
            R.drawable.img_tutorial_seehyang
        ),
        EDITOR(
            R.string.tutorial_editor_title,
            R.string.tutorial_editor_description,
            R.drawable.img_tutorial_editor
        ),
        DETAIL(
            R.string.tutorial_detail_title,
            R.string.tutorial_detail_description,
            R.drawable.img_tutorial_detail
        )
    }

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

        val page = requireArguments().getInt(PAGE_TYPE)
        binding.tutorialImage.loadImage(
            glideRequests,
            OnBoardingType.values()[page].imageRes
        )
        binding.title.text = getString(OnBoardingType.values()[page].titleRes)
        binding.description.text = getString(OnBoardingType.values()[page].descRes)
    }
}