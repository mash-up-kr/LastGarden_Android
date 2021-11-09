package com.mashup.lastgarden.ui.sign

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mashup.lastgarden.R
import com.mashup.lastgarden.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val firebaseAuth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        setupNavController()
        initFirebaseAuth()
    }

    private fun initFirebaseAuth() {
        firebaseAuth.currentUser?.let {
            moveMainActivity()
        }
    }

    private fun setupNavController() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.navHostFragment)
                    as NavHostFragment).navController
    }

    private fun moveMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}