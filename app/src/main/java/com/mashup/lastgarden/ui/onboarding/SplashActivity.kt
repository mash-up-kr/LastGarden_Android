package com.mashup.lastgarden.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mashup.base.image.GlideApp
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ActivitySplashBinding
import com.mashup.lastgarden.ui.sign.SignActivity
import dagger.hilt.android.AndroidEntryPoint
import com.mashup.lastgarden.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: OnBoardingViewModel by viewModels()

    companion object {
        private const val DELAY_TRANSITION = 300L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onSetupViews()
        onBindViewModelsOnCreate()
    }

    private fun onSetupViews() {
        GlideApp.with(this)
            .load(R.drawable.ic_splash_logo)
            .into(binding.splashImageView)
    }

    private fun onBindViewModelsOnCreate() {
        viewModel.isShowOnBoarding.observe(this) { isShowOnBoarding ->
            if (isShowOnBoarding) {
                showOnBoardingScreen()
            } else {
                showSignScreen()
            }
        }
    }

    private fun showOnBoardingScreen() {
        lifecycleScope.launch {
            delay(DELAY_TRANSITION)
            val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showSignScreen() {
        lifecycleScope.launch {
            delay(DELAY_TRANSITION)
            val intent = Intent(this@SplashActivity, SignActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}