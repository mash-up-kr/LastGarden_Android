package com.mashup.lastgarden.ui.scent

import android.os.Bundle
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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
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

    private lateinit var perfumeStoryPagingAdapter: ScentPagingAdapter
    private lateinit var perfumeStoryAdapter: ScentViewPagerAdapter
    private var listSize = 0

    val perfumeId by lazy { arguments?.getInt("perfumeId") ?: 0 }

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        perfumeStoryPagingAdapter = ScentPagingAdapter(glideRequests, this)
        perfumeStoryAdapter = ScentViewPagerAdapter(glideRequests, this)
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
                bundleOf("perfumeId" to perfumeId)
            )
        }
        binding.scentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                viewModel.setScrollPosition(
                    (recyclerView.layoutManager as? LinearLayoutManager)
                        ?.findLastCompletelyVisibleItemPosition() ?: 0
                )
            }
        })
    }

    override fun onBindViewModelsOnViewCreated() {
        super.onBindViewModelsOnViewCreated()

        viewModel.storySize.observe(viewLifecycleOwner) {
            listSize = it
        }

        viewModel.perfumeStoryList.observe(viewLifecycleOwner) { storyItems ->
            perfumeStoryAdapter.submitList(storyItems) {
                binding.detailButton.isVisible = false
            }
        }

        viewModel.perfumeStory.observe(viewLifecycleOwner) {
            setPerfumeStory(it)
            binding.sortButton.isVisible = false
            binding.detailButton.isVisible = false
            binding.pageCountTextView.isVisible = false
        }

        viewModel.emitStoryList.observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenCreated {
                viewModel.pagingStoryList.collectLatest {
                    perfumeStoryPagingAdapter.submitData(it)
                }
            }
            binding.sortButton.isVisible = false
        }

        viewModel.storyPosition.observe(viewLifecycleOwner) { storyIndex ->
            binding.scentRecyclerView.scrollToPosition(storyIndex)
            binding.pageCountTextView.text = StringFormatter.formatPageCount(
                requireActivity(), storyIndex, listSize
            )
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
        if (perfumeId == 0) {
            binding.scentRecyclerView.adapter = perfumeStoryAdapter
        } else {
            binding.scentRecyclerView.adapter = perfumeStoryPagingAdapter
        }
        val animator = binding.scentRecyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
        PagerSnapHelper().attachToRecyclerView(binding.scentRecyclerView)
        binding.scentRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
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
            nicknameTextView.text = item.userNickname
            dateTextView.text =
                StringFormatter.convertDate(requireActivity().resources, item.createdAt)
            tagListTextView.text = item.tags?.joinToString(" ") { "#" + it.contents + " " }
            likeCountTextView.text = item.likeCount?.let { StringFormatter.formatNumber(it) }
            //TODO like 이미지 설정
        }
    }

    private fun bindImageView(item: Story) {
        binding.includeScentLayout.run {
            glideRequests.load(item.imageUrl).into(scentImageView)
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
        viewModel.likeStory(scentId)
    }

}