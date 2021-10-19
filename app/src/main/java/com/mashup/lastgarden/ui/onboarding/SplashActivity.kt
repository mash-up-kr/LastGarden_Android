package com.mashup.lastgarden.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mashup.base.image.GlideApp
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlideApp.with(this)
            .load(R.drawable.ic_splash_logo)
            .into(binding.splashImageView)

        lifecycleScope.launch {
            delay(3000)

            val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}