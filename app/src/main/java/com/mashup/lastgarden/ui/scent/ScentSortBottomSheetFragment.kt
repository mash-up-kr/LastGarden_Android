package com.mashup.lastgarden.ui.scent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ScentSortBottomSheetBinding

class ScentSortBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding by autoCleared<ScentSortBottomSheetBinding>()
    private val viewModel: ScentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScentSortBottomSheetBinding.inflate(inflater, container, false)

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.finishButton.setOnClickListener {
            when (binding.radioGroup.checkedRadioButtonId) {
                R.id.radioTopButton -> viewModel.setSortOrder(Sort.POPULARITY)
                R.id.radioMiddleButton -> viewModel.setSortOrder(Sort.LATEST)
                R.id.radioBottomButton -> viewModel.setSortOrder(Sort.MOST_COMMENTS)
            }
            dismiss()
        }

        viewModel.sortOrder.observe(this, {
            when (it) {
                Sort.POPULARITY -> binding.radioGroup.check(R.id.radioTopButton)
                Sort.LATEST -> binding.radioGroup.check(R.id.radioMiddleButton)
                else -> binding.radioGroup.check(R.id.radioBottomButton)
            }
        })

        return binding.root
    }
}