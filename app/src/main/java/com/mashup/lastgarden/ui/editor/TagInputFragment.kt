package com.mashup.lastgarden.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.base.utils.dp
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentTagBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TagInputFragment : BaseViewModelFragment() {

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

        setUiOfButton()
        setUiOfEditText()
    }

    override fun onBindViewModelsOnViewCreated() {
        super.onBindViewModelsOnViewCreated()

        editorViewModel.tagList.observe(viewLifecycleOwner) { tagList ->
            tagList.onEach { tag ->
                addTagIntoChipGroup(tag)
            }
        }
    }

    private fun setUiOfButton() {
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
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
        binding.tagGroup.addView(createTagAsChip(tag))
    }

    private fun createTagAsChip(tag: String) = Chip(requireContext()).apply {
        setTag(tag)
        text = getString(R.string.tag_regex, tag)
        shapeAppearanceModel = ShapeAppearanceModel().apply {
            withCornerSize(4.dp.toFloat())
        }
        isCloseIconVisible = true
        closeIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_chip_closed, null)
        closeIconStartPadding = 10.dp.toFloat()

        chipBackgroundColor = ContextCompat.getColorStateList(requireContext(), R.color.colorGrey6)
        setTextColor(ResourcesCompat.getColor(resources, R.color.point, null))
        setOnCloseIconClickListener { closedChip ->
            binding.tagGroup.removeView(closedChip)
            binding.confirmButton.isEnabled = binding.tagGroup.checkedChipIds.isNotEmpty()
        }
    }
}