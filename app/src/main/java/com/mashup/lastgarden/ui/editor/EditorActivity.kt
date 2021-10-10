package com.mashup.lastgarden.ui.editor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.lastgarden.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditorActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IMAGE_URL = "extra_image_url"

        fun newIntent(
            context: Context,
            imageUrl: String
        ) = Intent(context, EditorActivity::class.java).apply {
            putExtra(EXTRA_IMAGE_URL, imageUrl)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
    }
}