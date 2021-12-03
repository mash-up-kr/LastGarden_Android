package com.mashup.lastgarden.ui.sign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mashup.lastgarden.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        setupNavController()
    }

    private fun setupNavController() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.navHostFragment)
                    as NavHostFragment).navController
    }
}