package com.mashup.lastgarden.ui.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.chip.Chip
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentTagBinding
import com.mashup.lastgarden.databinding.ItemTagBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TagFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentTagBinding>()
    private val viewModel by activityViewModels<UploadViewModel>()

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTagBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)

        setUiOfToolBar()
        setUiOfButton()
        setUiOfEditText()
    }

    override fun onBindViewModelsOnViewCreated() {
        super.onBindViewModelsOnViewCreated()

        viewModel.tagSet.observe(viewLifecycleOwner) { tagList ->
            binding.confirmButton.isEnabled = tagList.isNotEmpty()
            drawChipGroup(tagList)
        }
    }

    private fun setUiOfToolBar() {
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun setUiOfButton() {
        binding.confirmButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.addTagButton.setOnClickListener {
            val tag = binding.tagEditText.text.toString()
            viewModel.addTag(tag)
            binding.tagEditText.setText("")
        }
    }

    private fun setUiOfEditText() {
        binding.tagEditText.doOnTextChanged { text, _, _, _ ->
            binding.addTagButton.isEnabled = !text.isNullOrEmpty()
        }
    }

    private fun drawChipGroup(tagList: Set<String>) = binding.tagGroup.apply {
        removeAllViews()
        tagList.forEach { tag ->
            addView(
                bindTagAsChip(
                    chip = ItemTagBinding.inflate(LayoutInflater.from(requireContext())).root,
                    tag = tag
                )
            )
        }
    }

    private fun bindTagAsChip(chip: Chip, tag: String) = chip.apply {
        this.tag = tag
        text = getString(R.string.tag_regex, tag)
        setOnCloseIconClickListener {
            viewModel.removeTag(tag)
        }
    }
}