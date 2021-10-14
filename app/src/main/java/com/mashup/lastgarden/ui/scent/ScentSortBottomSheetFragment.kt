package com.mashup.lastgarden.ui.scent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ScentSortBottomSheetBinding

class ScentSortBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding by autoCleared<ScentSortBottomSheetBinding>()
    private val viewModel: ScentViewModel by activityViewModels()

    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScentSortBottomSheetBinding.inflate(inflater, container, false)

        viewModel.position.observe(this, {
            binding.radioGroup.check(binding.radioGroup.getChildAt(it).id)
        })

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioTopButton -> position = 0
                R.id.radioMiddleButton -> position = 1
                R.id.radioBottomButton -> position = 2
            }
        }

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.finishButton.setOnClickListener {
            viewModel._position.value = position
            dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}