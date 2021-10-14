package com.mashup.lastgarden.ui.scent

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ActivityScentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class ScentActivity : AppCompatActivity(), ScentViewPagerAdapter.OnClickListener {
    private lateinit var binding: ActivityScentBinding
    private val viewModel: ScentViewModel by viewModels()

    @Inject
    @Named("Activity")
    lateinit var glideRequests: GlideRequests

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomSheet()
        viewModel.getScentList(0)
        viewModel.scentList.observe(this, {
            binding.scentViewPager.adapter = ScentViewPagerAdapter(it, glideRequests)
        })

        viewModel.position.observe(this, {
            when (it) {
                0 -> binding.sortButton.text = getString(R.string.radio_btn_top)
                1 -> binding.sortButton.text = getString(R.string.radio_btn_middle)
                2 -> binding.sortButton.text = getString(R.string.radio_btn_bottom)
            }
            //TODO it 번째로 정렬 함수 호출
        })

        binding.closeButton.setOnClickListener {
            finish()
        }
    }

    private fun setupBottomSheet() {
        binding.sortButton.setOnClickListener {
            val bottomSheetDialog = ScentSortBottomSheetFragment()
            bottomSheetDialog.show(supportFragmentManager, "")
        }
    }

    override fun onCommentClick(scentId: Int) {
        //TODO id 해당하는 Comment Bottom Sheet 띄우기
    }

    override fun onLikeClick(scentId: Int) {
        //TODO id 포스트 좋아요 처리
    }
}