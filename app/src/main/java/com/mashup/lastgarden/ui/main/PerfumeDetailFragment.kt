package com.mashup.lastgarden.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.base.utils.dp
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.databinding.FragmentPerfumeDetailBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@AndroidEntryPoint
class PerfumeDetailFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentPerfumeDetailBinding>()

    private val viewModel by activityViewModels<PerfumeDetailViewModel>()

    private lateinit var viewPagerAdapter: PerfumeDetailPagerAdapter

    @Inject
    lateinit var glideRequests: GlideRequests

    private var perfumeId = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfumeDetailBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        initToolbar()
        initViewPager()
        initTabLayout()
        addListeners()
    }

    override fun onBindViewModelsOnViewCreated() {
        val perfumeId = requireArguments().getInt("perfumeId")
        viewModel.setPerfumeId(perfumeId)
        viewModel.fetchPerfumeDetail(perfumeId)
        viewModel.fetchStoryCount(perfumeId)
        lifecycleScope.launchWhenCreated {
            viewModel.perfumeDetailItem
                .filterNotNull()
                .collectLatest {
                    setPerfumeDetail(it)
                    viewModel.setPerfumeLike()
                }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.likeCount
                .filterNotNull()
                .collectLatest {
                    binding.likeCountTextView.text = it.toString()
                }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.isLiked
                .filterNotNull()
                .collectLatest { isLiked ->
                    if (isLiked) {
                        binding.likeImageView.setImageResource(R.drawable.ic_like)
                    } else {
                        binding.likeImageView.setImageResource(R.drawable.ic_like_empty)
                    }
                }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.storyCount
                .filterNotNull()
                .collectLatest { count ->
                    if (count > 0) {
                        binding.nextButton.isEnabled = true
                        binding.nextButton.text = getString(R.string.perfume_detail_scent_button)
                    } else {
                        binding.nextButton.isEnabled = false
                        binding.nextButton.text =
                            getString(R.string.perfume_detail_scent_button_enabled)
                    }
                }
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.navigationIcon?.setTint(Color.BLACK)
    }

    private fun initViewPager() {
        viewPagerAdapter = PerfumeDetailPagerAdapter(this)
        viewPagerAdapter.fragments = listOf(
            PerfumeInformationFragment(),
            ScentListFragment()
        )
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updatePagerHeight(position, binding.viewPager)
            }
        })
    }

    private fun updatePagerHeight(position: Int, viewPager: ViewPager2) {
        if (position == 0) {
            viewPager.post {
                val view =
                    viewPager.findViewById<NestedScrollView>(R.id.perfumeInformationContainer)
                view.measure(
                    View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.UNSPECIFIED,
                        View.MeasureSpec.UNSPECIFIED
                    ),
                    View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.UNSPECIFIED,
                        View.MeasureSpec.UNSPECIFIED
                    )
                )

                if (viewPager.layoutParams.height != view.measuredHeight) {
                    viewPager.updateLayoutParams {
                        val maxHeight = resources.displayMetrics.heightPixels - 246.dp
                        height = view.measuredHeight.coerceAtMost(maxHeight)
                    }
                }
            }
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_perfume)
                1 -> tab.setIcon(R.drawable.ic_grid)
            }
        }.attach()
    }

    private fun setPerfumeDetail(perfumeItem: Perfume) {
        binding.apply {
            titleTextView.text = perfumeItem.koreanName
            titleEngTextView.text = perfumeItem.name
            photoImageView.loadImage(glideRequests, perfumeItem.thumbnailUrl)
        }
    }

    private fun addListeners() {
        binding.likeButton.setOnSingleClickListener {
            viewModel.likePerfume(perfumeId)
        }
        binding.nextButton.setOnClickListener {
            findNavController().navigate(
                R.id.actionPerfumeDetailFragmentToScentFragment,
                bundleOf("perfumeId" to perfumeId)
            )
        }
    }
}