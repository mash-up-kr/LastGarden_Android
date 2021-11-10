package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentPerfumeInformationBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment

class PerfumeInformationFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentPerfumeInformationBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfumeInformationBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        setNoteButtonChecked()
    }

    private fun setNoteButtonChecked() {
        binding.perfumePyramidInclude.topCheckBox.setOnClickListener {
            allButtonUnchecked()
            binding.perfumePyramidInclude.apply {
                topCheckBox.isChecked = true
                pyramidImageView.setImageResource(R.drawable.ic_pyramid_top)
                pyramidContentTextView.text = "시트러스 어코드, 페티그레인, 블랙커런트, 레드프룻, 피치"
            }
        }
        binding.perfumePyramidInclude.middleCheckBox.setOnClickListener {
            allButtonUnchecked()
            binding.perfumePyramidInclude.apply {
                middleCheckBox.isChecked = true
                pyramidImageView.setImageResource(R.drawable.ic_pyramid_middle)
                pyramidContentTextView.text = "화이트플라워, 튜베로즈, 재스민, 일랑일랑, 오렌지블로썸, 코코넛"
            }
        }
        binding.perfumePyramidInclude.baseCheckBox.setOnClickListener {
            allButtonUnchecked()
            binding.perfumePyramidInclude.apply {
                baseCheckBox.isChecked = true
                pyramidImageView.setImageResource(R.drawable.ic_pyramid_base)
                pyramidContentTextView.text = "샌달우드, 바닐라, 화이트머스크"
            }
        }
    }

    private fun allButtonUnchecked() {
        binding.perfumePyramidInclude.apply {
            topCheckBox.isChecked = false
            middleCheckBox.isChecked = false
            baseCheckBox.isChecked = false
        }
    }
}