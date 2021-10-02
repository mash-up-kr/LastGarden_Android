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
        setNoteButtonChecked()
        return binding.root
    }

    private fun setNoteButtonChecked() {
        binding.checkBoxTop.setOnClickListener {
            allButtonUnchecked()
            binding.apply {
                checkBoxTop.isChecked = true
                imageViewPyramid.setImageResource(R.drawable.ic_pyramid_top)
                textViewPyramidContent.text = "시트러스 어코드, 페티그레인, 블랙커런트, 레드프룻, 피치"
            }
        }
        binding.checkBoxMiddle.setOnClickListener {
            allButtonUnchecked()
            binding.apply {
                checkBoxMiddle.isChecked = true
                imageViewPyramid.setImageResource(R.drawable.ic_pyramid_middle)
                textViewPyramidContent.text = "화이트플라워, 튜베로즈, 재스민, 일랑일랑, 오렌지블로썸, 코코넛"
            }
        }
        binding.checkBoxBase.setOnClickListener {
            allButtonUnchecked()
            binding.apply {
                checkBoxBase.isChecked = true
                imageViewPyramid.setImageResource(R.drawable.ic_pyramid_base)
                textViewPyramidContent.text = "샌달우드, 바닐라, 화이트머스크"
            }
        }
    }

    private fun allButtonUnchecked() {
        binding.apply {
            checkBoxTop.isChecked = false
            checkBoxMiddle.isChecked = false
            checkBoxBase.isChecked = false
        }
    }
}