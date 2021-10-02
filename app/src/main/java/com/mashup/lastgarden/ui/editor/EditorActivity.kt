package com.mashup.lastgarden.ui.editor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mashup.lastgarden.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels

@AndroidEntryPoint
class EditorActivity : AppCompatActivity() {
    private val viewModel by viewModels<EditorViewModel>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        setupNavController()
        showCropActivity()
    }

    private fun setupNavController() {
        navController = (supportFragmentManager.findFragmentById(R.id.navHostFragment)
            as NavHostFragment).navController
    }

    private fun showCropActivity() {
        CropImage.activity()
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri
                viewModel.setImageUri(resultUri)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}