package com.mashup.lastgarden.ui.upload.editor

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.mashup.lastgarden.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditorActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel: EditorViewModel by viewModels()

    private val cropImageLauncher = registerForActivityResult(
        CropImageContract()
    ) { result ->
        if (result.isSuccessful) {
            result.getBitmap(this)?.let { bitmap ->
                viewModel.setEditedImage(bitmap)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        setupNavController()
        showImagePicker()
    }

    private fun setupNavController() {
        navController = (supportFragmentManager.findFragmentById(R.id.navHostFragment)
                as NavHostFragment).navController
    }

    private fun showImagePicker() {
        cropImageLauncher.launch(options { setGuidelines(CropImageView.Guidelines.ON) })
    }
}