package com.mashup.lastgarden.ui.scent

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mashup.lastgarden.databinding.ActivityScentBinding

class ScentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScentBinding
    private val viewModel: ScentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getScentList()
        viewModel.scentList.observe(this, {
            binding.vpScent.adapter = ScentViewPagerAdapter(it)
        })
    }
}