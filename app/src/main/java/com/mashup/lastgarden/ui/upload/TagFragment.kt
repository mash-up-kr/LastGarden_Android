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
import com.mashup.lastgarden.ui.upload.editor.EditorViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TagFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentTagBinding>()
    private val editorViewModel by activityViewModels<EditorViewModel>()

    @Inject
    lateinit var glideRequests: GlideRequests

    companion object {
        private const val tagPrefix = "#"
    }

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

        editorViewModel.tagList.observe(viewLifecycleOwner) { tagList ->
            binding.confirmButton.isEnabled = tagList.isNotEmpty()
            tagList.onEach { tag ->
                addTagIntoChipGroup(tag)
            }
        }
    }

    private fun setUiOfToolBar() {
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun setUiOfButton() {
        binding.confirmButton.setOnClickListener {
            saveTagWithChipIds(binding.tagGroup.checkedChipIds)
            findNavController().popBackStack()
        }
        binding.addTagButton.setOnClickListener {
            val tag = binding.tagEditText.text.toString()
            binding.tagEditText.setText("")
            addTagIntoChipGroup(tag)
        }
    }

    private fun setUiOfEditText() {
        binding.tagEditText.doOnTextChanged { text, _, _, _ ->
            binding.addTagButton.isEnabled = !text.isNullOrEmpty()
        }
    }

    private fun saveTagWithChipIds(ids: List<Int>) {
        editorViewModel.setTagList(
            ids.map { chipId ->
                val chip = binding.tagGroup.findViewById<Chip>(chipId)
                return@map (chip.tag as? String) ?: chip.text.removePrefix(tagPrefix).toString()
            }
        )
    }

    private fun addTagIntoChipGroup(tag: String) {
        binding.confirmButton.isEnabled = true
        createTagAsChip(tag)
    }

    private fun createTagAsChip(tag: String) {
        with(ItemTagBinding.inflate(layoutInflater, binding.tagGroup, true).root) {
            this.tag = tag
            text = getString(R.string.tag_regex, tag)
            setOnCloseIconClickListener { closedChip ->
                binding.tagGroup.removeView(closedChip)
                binding.confirmButton.isEnabled = binding.tagGroup.checkedChipIds.isNotEmpty()
            }
        }
    }
}