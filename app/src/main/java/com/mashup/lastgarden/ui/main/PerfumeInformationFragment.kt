package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Note
import com.mashup.lastgarden.databinding.FragmentPerfumeInformationBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PerfumeInformationFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentPerfumeInformationBinding>()

    private val viewModel by activityViewModels<PerfumeDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfumeInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        addListenersOnCheckButton()
    }

    override fun onBindViewModelsOnCreate() {
        viewModel.fetchPerfumeDetail()
    }

    override fun onBindViewModelsOnViewCreated() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.perfumeDetailItem
                .filterNotNull()
                .collectLatest {
                    viewModel.initSelectedNote(it)
                    setMainAccords(it.accords)
                    setPerfumePyramidVisibility()
                }
        }
        viewLifecycleOwner.lifecycleScope.launch {
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.noteContents
                .filterNotNull()
                .collectLatest { noteContents ->
                    if (viewModel.selectedNoteValue != null) {
                        binding.perfumePyramidInclude.pyramidContentTextView.text = noteContents
                    } else {
                        binding.perfumePyramidInclude.noteContentTextView.text = noteContents
                    }
                }
        }
    }

    private fun setMainAccords(accords: List<Note>?) {
        binding.mainAccordsInclude.accordContentTextView.text =
            accords?.let { TextUtils.join(", ", it.map { accord -> accord.koreanName }) }
    }

    private fun setPerfumePyramidVisibility() {
        if (viewModel.selectedNoteValue == null) {
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