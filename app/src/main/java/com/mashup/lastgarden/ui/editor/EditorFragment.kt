package com.mashup.lastgarden.ui.editor

import com.mashup.lastgarden.R
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.FragmentEditorBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import ja.burhanrashid52.photoeditor.*
import ja.burhanrashid52.photoeditor.PhotoEditor.OnSaveListener
import javax.inject.Inject


@AndroidEntryPoint
class EditorFragment : BaseViewModelFragment(), OnPhotoEditorListener {

    private var binding by autoCleared<FragmentEditorBinding>()
    private val activityViewModel by activityViewModels<EditorViewModel>()

    @Inject
    lateinit var glideRequests: GlideRequests

    private var editor: PhotoEditor? = null
    private val saveHelper by lazy {
        FileSaveHelper(requireActivity() as AppCompatActivity)
    }

    companion object {
        private const val READ_WRITE_STORAGE = 52
    }

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
        initEditorView()
        observeViewModel()

        binding.photoEditorView.source.scaleType =
            ImageView.ScaleType.CENTER_INSIDE

        binding.closeBtn.setOnClickListener {
            requireActivity().finish()
        }
        binding.textBtn.setOnClickListener {
            val textEditorDialogFragment = TextEditorDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TextEditorDialogFragment.EXTRA_INPUT_TEXT, "")
                    putInt(TextEditorDialogFragment.EXTRA_COLOR_CODE, android.R.color.white)
                }

                setOnTextEditorListener(
                    object : TextEditorDialogFragment.TextEditor {
                        override fun onDone(inputText: String?, colorCode: Int) {
                            val styleBuilder = TextStyleBuilder()
                            styleBuilder.withTextColor(colorCode)
                            editor?.addText(inputText, styleBuilder)
                        }
                    })
            }

            textEditorDialogFragment.show(childFragmentManager, "")
        }
        binding.floatingButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_editorFragment_to_editorUploadFragment
            )
        }
    }

    private fun observeViewModel() {
        activityViewModel.imageUri.observe(this) { uri ->
            setImageUsingFileUri(uri)
        }
    }

    private fun setImageUsingFileUri(uri: Uri?) {
        uri?.let {
            binding.photoEditorView.source.loadImage(
                glideRequests,
                uri.toString()
            )
        }
    }

    private fun initEditorView() {
        editor = PhotoEditor.Builder(requireContext(), binding.photoEditorView)
            .setPinchTextScalable(true)
            .setClipSourceImage(true)
            .build()

        editor?.setOnPhotoEditorListener(this)
    }

    override fun onEditTextChangeListener(rootView: View, text: String, colorCode: Int) {
        val color = ContextCompat.getColor(requireContext(), android.R.color.white)
        val textEditorDialogFragment = TextEditorDialogFragment().apply {
            arguments = Bundle().apply {
                putString(TextEditorDialogFragment.EXTRA_INPUT_TEXT, text)
                putInt(TextEditorDialogFragment.EXTRA_COLOR_CODE, color)
            }

            setOnTextEditorListener(object :
                TextEditorDialogFragment.TextEditor {
                override fun onDone(inputText: String?, colorCode: Int) {
                    val styleBuilder = TextStyleBuilder()
                    styleBuilder.withTextColor(colorCode)
                    editor?.editText(rootView, inputText, styleBuilder)
                }
            })
        }
        textEditorDialogFragment.show(childFragmentManager, "")
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

    @SuppressLint("MissingPermission")
    private fun saveImage() {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val hasStoragePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PERMISSION_GRANTED
        if (hasStoragePermission) {
            saveHelper.createFile(
                fileName
            ) { created, filePath, error, uri ->
                if (created) {
                    val saveSettings = SaveSettings.Builder()
                        .setClearViewsEnabled(true)
                        .setTransparencyEnabled(true)
                        .build()

                    editor?.saveAsFile(filePath, saveSettings, object : OnSaveListener {
                        override fun onSuccess(imagePath: String) {
                            saveHelper.notifyThatFileIsNowPubliclyAvailable(
                                requireActivity().contentResolver
                            )
                            binding.photoEditorView.source.setImageURI(uri)
                            activityViewModel.setImageUri(uri)
                        }

                        override fun onFailure(exception: Exception) {

                        }
                    })
                }
            }
        } else {
            requireActivity().requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), READ_WRITE_STORAGE
            )
        }
    }

    private fun isPermissionGranted(isGranted: Boolean, permission: String?) {
        if (isGranted) {
            saveImage()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_WRITE_STORAGE -> isPermissionGranted(
                grantResults[0] == PackageManager.PERMISSION_GRANTED,
                permissions[0]
            )
        }
    }
}