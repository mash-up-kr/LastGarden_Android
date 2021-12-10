package com.mashup.lastgarden.ui.upload.editor

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mashup.base.autoCleared
import com.mashup.base.extensions.showToast
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentEditorBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.upload.UploadViewModel
import dagger.hilt.android.AndroidEntryPoint
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener
import ja.burhanrashid52.photoeditor.OnSaveBitmap
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.TextStyleBuilder
import ja.burhanrashid52.photoeditor.ViewType
import javax.inject.Inject

@AndroidEntryPoint
class EditorFragment : BaseViewModelFragment(), OnPhotoEditorListener {

    private var binding by autoCleared<FragmentEditorBinding>()
    private val viewModel by activityViewModels<UploadViewModel>()

    private var editor: PhotoEditor? = null

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditorBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        setUiOfEditorView()
        setUiOfAddTextButton()

        binding.closeButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.floatingButton.setOnClickListener {
            saveEditedImage()
        }
    }

    private fun setUiOfEditorView() {
        editor = PhotoEditor.Builder(requireContext(), binding.photoEditorView)
            .setPinchTextScalable(true)
            .setClipSourceImage(true)
            .build().apply {
                setOnPhotoEditorListener(this@EditorFragment)
            }
    }

    private fun setUiOfAddTextButton() {
        binding.addTextButton.setOnClickListener {
            showAddTextDialog(
                targetView = null,
                defaultText = "",
                defaultColor = ContextCompat.getColor(requireContext(), R.color.point)
            )
        }
    }

    private fun saveEditedImage() {
        editor?.saveAsBitmap(object : OnSaveBitmap {
            override fun onBitmapReady(saveBitmap: Bitmap?) {
                saveBitmap?.let {
                    viewModel.setEditedImage(saveBitmap)
                    findNavController().navigate(
                        R.id.actionEditorFragmentToUploadFragment
                    )
                }
            }

            override fun onFailure(e: Exception?) {
                showToast(R.string.editor_failed_save_edited_image)
                findNavController().popBackStack()
            }
        })
    }

    private fun showAddTextDialog(targetView: View?, defaultText: String?, defaultColor: Int?) {
        TextEditorDialogFragment().apply {
            arguments = Bundle().apply {
                putString(TextEditorDialogFragment.EXTRA_INPUT_TEXT, defaultText)
                putInt(TextEditorDialogFragment.EXTRA_COLOR, defaultColor ?: 0)
            }
            setOnTextEditorListener { text, color ->
                val styleBuilder = TextStyleBuilder().apply {
                    withTextColor(color ?: defaultColor ?: 0)
                }

                if (targetView == null) {
                    editor?.addText(text, styleBuilder)
                } else {
                    editor?.editText(targetView, text, styleBuilder)
                }
            }
        }.also { dialog ->
            dialog.show(childFragmentManager, TextEditorDialogFragment::class.java.simpleName)
        }
    }

    override fun onBindViewModelsOnViewCreated() {
        viewModel.editedImage.observe(this) { imageBitmap ->
            binding.photoEditorView.source.setImageBitmap(imageBitmap)
        }
    }

    override fun onEditTextChangeListener(rootView: View?, text: String?, colorCode: Int) {
        showAddTextDialog(rootView, text, colorCode)
    }

    override fun onAddViewListener(viewType: ViewType?, numberOfAddedViews: Int) {

    }

    override fun onRemoveViewListener(viewType: ViewType?, numberOfAddedViews: Int) {

    }

    override fun onStartViewChangeListener(viewType: ViewType?) {

    }

    override fun onStopViewChangeListener(viewType: ViewType?) {

    }

    override fun onTouchSourceImage(event: MotionEvent?) {

    }
}