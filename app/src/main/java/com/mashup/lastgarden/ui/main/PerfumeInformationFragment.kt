package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentPerfumeInformationBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class PerfumeInformationFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentPerfumeInformationBinding>()

    private val viewModel by viewModels<PerfumeDetailViewModel>()

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
        addListenersOnCheckButton()
    }

    override fun onBindViewModelsOnCreate() {
        lifecycleScope.launchWhenCreated {
            viewModel.perfumeDetailItem
                .filterNotNull()
                .collectLatest {
                    setMainAccords()
                    setPerfumePyramidVisibility()
                }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.selectedNote
                .filterNotNull()
                .collectLatest { selectedNote ->
                    binding.perfumePyramidInclude.topCheckBox.isChecked =
                        selectedNote == PerfumeDetailViewModel.PerfumeDetailNote.TOP
                    binding.perfumePyramidInclude.middleCheckBox.isChecked =
                        selectedNote == PerfumeDetailViewModel.PerfumeDetailNote.MIDDLE
                    binding.perfumePyramidInclude.baseCheckBox.isChecked =
                        selectedNote == PerfumeDetailViewModel.PerfumeDetailNote.BASE
                    viewModel.setNoteContents()
                }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.noteContents
                .filterNotNull()
                .collectLatest { noteContents ->
                    if (viewModel.selectedNote.value != null) {
                        binding.perfumePyramidInclude.pyramidContentTextView.text = noteContents
                    } else {
                        binding.perfumePyramidInclude.noteContentTextView.text = noteContents
                    }
                }
        }
    }

    private fun setMainAccords() {
        binding.mainAccordsInclude.accordContentTextView.text = viewModel.mainAccords.value
    }

    private fun setPerfumePyramidVisibility() {
        if (viewModel.selectedNote.value == null) {
            binding.perfumePyramidInclude.apply {
                noteContainer.isVisible = true
                perfumePyramidContainer.isVisible = false
            }
        } else {
            binding.perfumePyramidInclude.apply {
                noteContainer.isVisible = false
                perfumePyramidContainer.isVisible = true
            }
        }
    }

    private fun addListenersOnCheckButton() {
        binding.perfumePyramidInclude.apply {
            topCheckBox.setOnClickListener {
                viewModel.setSelectedNote(PerfumeDetailViewModel.PerfumeDetailNote.TOP)
                pyramidImageView.setImageResource(R.drawable.ic_pyramid_top)
            }
            middleCheckBox.setOnClickListener {
                viewModel.setSelectedNote(PerfumeDetailViewModel.PerfumeDetailNote.MIDDLE)
                pyramidImageView.setImageResource(R.drawable.ic_pyramid_middle)
            }
            baseCheckBox.setOnClickListener {
                viewModel.setSelectedNote(PerfumeDetailViewModel.PerfumeDetailNote.BASE)
                pyramidImageView.setImageResource(R.drawable.ic_pyramid_base)
            }
        }
    }
}