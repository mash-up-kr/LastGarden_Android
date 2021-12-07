package com.mashup.lastgarden.ui.scent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.databinding.FragmentScentBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.scent.comment.ScentCommentBottomSheetFragment
import com.mashup.lastgarden.utils.convertDate
import com.mashup.lastgarden.utils.formatNumber
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ScentFragment : BaseViewModelFragment(), ScentViewPagerAdapter.OnClickListener {
    private var binding by autoCleared<FragmentScentBinding>()
    private val viewModel: ScentViewModel by activityViewModels()

    private lateinit var perfumeStoryAdapter: ScentPagingAdapter

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        perfumeStoryAdapter = ScentPagingAdapter(glideRequests, this)
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
            findNavController().popBackStack()
        }
        binding.detailButton.setOnClickListener {
            findNavController().navigate(
                R.id.actionScentFragmentToPerfumeDetailFragment,
                bundleOf(
                    "perfumeId" to requireArguments().getInt("perfumeId")
                )
            )
            val lastPosition =
                (binding.scentRecyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            viewModel.setScrollPosition(lastPosition)
        }
    }

    override fun onBindViewModelsOnCreate() {
        val perfumeId = requireArguments().getInt("perfumeId")
        if (perfumeId != 0) {
            lifecycleScope.launchWhenCreated {
                viewModel.getPerfumeStoryLists(perfumeId).collectLatest {
                    perfumeStoryAdapter.submitData(it)
                }
            }
        } else {
            val storyPosition = requireArguments().getInt("storyPosition")
            getPerfumeStory(storyPosition)
        }
    }

    override fun onBindViewModelsOnViewCreated() {
        super.onBindViewModelsOnViewCreated()

        viewModel.perfumeStoryList.observe(viewLifecycleOwner) {
            binding.scentRecyclerView.adapter = ScentViewPagerAdapter(it, glideRequests, this)
            binding.detailButton.isVisible = true
            binding.sortButton.isVisible = false
            viewModel.storyPosition.value?.let { position ->
                binding.scentRecyclerView.scrollToPosition(
                    position
                )
            }
        }

        viewModel.perfumeStory.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.sortButton.isVisible = false
                setPerfumeStory(it)
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

    private fun getPerfumeStory(storyPosition: Int) {
        val mainStorySet: MainStorySet? = requireArguments().getParcelable("mainStorySet")
        val storyId = requireArguments().getInt("storyId")
        when {
            mainStorySet != null -> {
                viewModel.getTodayAndHotStoryList(mainStorySet)
                viewModel.setScrollPosition(storyPosition)
            }
            storyId != 0 -> {
                viewModel.getPerfumeStory(storyId)
            }
        }
    }

    private fun setupBottomSheet() {
        binding.sortButton.setOnClickListener {
            //TODO API 나오면 업데이트
        }
    }

    private fun setupRecyclerView() {
        val animator = binding.scentRecyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
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
            dateTextView.text = convertDate(requireActivity().resources, item.createdAt)
            tagListTextView.text = item.tags?.joinToString(" ") { "#" + it.contents + " " }
            likeCountTextView.text = item.likeCount?.let { formatNumber(it) }
        }
    }

    private fun bindImageView(item: Story) {
        binding.includeScentLayout.run {
            glideRequests.load(item.perfumeImageUrl).into(scentImageView)
            profileImageView.setImageUrl(glideRequests, item.userProfileImage)
            commentImageView.setOnClickListener { onCommentClick(item.storyId) }
            likeImageView.setOnClickListener { onLikeClick(item.storyId, 0) }
            likeImageView.loadImage(glideRequests, R.drawable.ic_dislike)
            likeImageView.loadImage(
                glideRequests,
                if (item.isLiked) R.drawable.ic_like else R.drawable.ic_dislike
            )
        }
    }

    override fun onCommentClick(scentId: Int) {
        ScentCommentBottomSheetFragment().show(requireActivity().supportFragmentManager, "")
    }

    override fun onLikeClick(scentId: Int, storyPosition: Int) {
        viewModel.getPerfumeStoryLike(scentId)
        getPerfumeStory(storyPosition)
    }

}