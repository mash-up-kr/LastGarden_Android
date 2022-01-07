package com.mashup.lastgarden.ui.scent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.databinding.FragmentScentBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.scent.comment.ScentCommentBottomSheetFragment
import com.mashup.lastgarden.utils.StringFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ScentFragment : BaseViewModelFragment(), ScentViewPagerAdapter.OnClickListener {
    private var binding by autoCleared<FragmentScentBinding>()
    private val viewModel: ScentViewModel by viewModels()

    private lateinit var perfumeStoryAdapter: ScentPagingAdapter

    val mainStorySet: MainStorySet? by lazy { arguments?.getParcelable("mainStorySet") }
    val storyIndex by lazy { arguments?.getInt("storyIndex") ?: 0 }
    val perfumeId by lazy { arguments?.getInt("perfumeId") ?: 0 }
    val storyId by lazy { arguments?.getInt("storyId") ?: 0 }

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        perfumeStoryAdapter = ScentPagingAdapter(glideRequests)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        setupBottomSheet()
        setupRecyclerView()

        binding.closeButton.setOnClickListener {
            requireActivity().finish()
        }
        binding.detailButton.setOnClickListener {
            findNavController().navigate(
                R.id.actionScentFragmentToPerfumeDetailFragment,
                bundleOf(
                    "perfumeId" to requireArguments().getInt("perfumeId")
                )
            )
        }
    }

    override fun onBindViewModelsOnCreate() {
        viewModel.setMainStoryList(mainStorySet, storyIndex)
        viewModel.setPerfumeId(perfumeId)
        viewModel.setStoryId(storyId)
    }

    override fun onBindViewModelsOnViewCreated() {
        super.onBindViewModelsOnViewCreated()

        viewModel.storyIdAndPerfumeIdSet.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.getTodayAndHotStoryList(it)
            }
        }

        viewModel.perfumeId.observe(viewLifecycleOwner) {
            if (it != 0) {
                viewModel.getPerfumeStoryList(it)
            }
        }

        viewModel.storyId.observe(viewLifecycleOwner) {
            if (it != 0) {
                viewModel.getPerfumeStory(it)
            }
        }

        viewModel.perfumeStoryList.observe(viewLifecycleOwner) {
            binding.scentRecyclerView.adapter = ScentViewPagerAdapter(it, glideRequests, this)
            binding.detailButton.isVisible = true
            binding.sortButton.isVisible = false
            binding.scentRecyclerView.scrollToPosition(storyIndex)
        }

        viewModel.perfumeStory.observe(viewLifecycleOwner) {
            binding.sortButton.isVisible = false
            setPerfumeStory(it)
        }

        viewModel.emitStoryList.observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenCreated {
                viewModel.pagingStoryList.collectLatest {
                    perfumeStoryAdapter.submitData(it)
                }
            }
        }

        viewModel.sortOrder.observe(viewLifecycleOwner) {
            when (it) {
                Sort.POPULARITY -> binding.sortButton.text = getString(R.string.radio_btn_top)
                Sort.LATEST -> binding.sortButton.text = getString(R.string.radio_btn_middle)
                else -> binding.sortButton.text = getString(R.string.radio_btn_bottom)
            }
            //TODO 필터 API 적용
        }
    }

    private fun setupBottomSheet() {
        binding.sortButton.setOnClickListener {
            //TODO API 나오면 업데이트
        }
    }

    private fun setupRecyclerView() {
        binding.scentRecyclerView.adapter = perfumeStoryAdapter
        binding.scentRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.scentRecyclerView)
    }

    private fun setPerfumeStory(item: Story) {
        binding.scentRecyclerView.isVisible = false
        binding.sortButton.isVisible = false
        binding.includeScentLayout.root.isVisible = true

        bindTextView(item)
        bindImageView(item)
    }

    private fun bindTextView(item: Story) {
        binding.includeScentLayout.run {
            pageCountTextView.isVisible = false
            nicknameTextView.text = item.userNickname
            dateTextView.text =
                StringFormatter.convertDate(requireActivity().resources, item.createdAt)
            tagListTextView.text = item.tags?.joinToString(" ") { "#" + it.contents + " " }
            commentCountTextView.text = item.commentCount?.let { StringFormatter.formatNumber(it) }
            likeCountTextView.text = item.likeCount?.let { StringFormatter.formatNumber(it) }
            //TODO like 이미지 설정
        }
    }

    private fun bindImageView(item: Story) {
        binding.includeScentLayout.run {
            profileImageView.setImageUrl(glideRequests, item.imageUrl)
            commentImageView.setOnClickListener { onCommentClick(item.storyId) }
            likeImageView.setOnClickListener { onLikeClick(item.storyId) }
            likeImageView.loadImage(glideRequests, R.drawable.ic_dislike)
            //TODO like 이미지 설정
        }
    }

    override fun onCommentClick(storyId: Int) {
        ScentCommentBottomSheetFragment().apply {
            arguments = bundleOf("storyId" to storyId)
        }.show(requireActivity().supportFragmentManager, null)
    }

    override fun onLikeClick(storyId: Int) {
        viewModel.getPerfumeStoryLike(storyId)
    }

}