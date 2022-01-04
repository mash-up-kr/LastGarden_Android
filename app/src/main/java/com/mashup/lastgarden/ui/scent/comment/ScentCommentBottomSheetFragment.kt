package com.mashup.lastgarden.ui.scent.comment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.base.autoCleared
import com.mashup.base.extensions.showToast
import com.mashup.base.image.GlideRequests
import com.mashup.base.utils.dp
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Comment
import com.mashup.lastgarden.databinding.ScentCommentBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ScentCommentBottomSheetFragment : BottomSheetDialogFragment(),
    ScentCommentPagingAdapter.OnClickListener {
    private var binding by autoCleared<ScentCommentBottomSheetBinding>()
    private val viewModel: ScentCommentViewModel by activityViewModels()

    private lateinit var scentCommentAdapter: ScentCommentPagingAdapter

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
        scentCommentAdapter = ScentCommentPagingAdapter(glideRequests, this)
        val storyId = arguments?.getInt("storyId")
        viewModel.setStoryId(storyId ?: 0)
        viewModel.getCommentList(storyId ?: 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScentCommentBottomSheetBinding.inflate(inflater, container, false)
        binding.commentScrollView.updateLayoutParams { height = 510.dp }
        setupResizeScrollView()

        binding.commentRecyclerView.adapter = scentCommentAdapter
        setupView()

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.applyButton.setOnClickListener {
            val contents = binding.addCommentEditText.text.toString()
            viewModel.storyId.value?.let { storyId -> viewModel.addComment(storyId, contents) }
            binding.addCommentEditText.text.clear()
            binding.addCommentEditText.clearFocus()
        }

        return binding.root
    }

    private fun setupView() {
        viewModel.commentResponse.observe(viewLifecycleOwner, {
            if (it == ScentCommentViewModel.AddCommentState.SUCCESS) {
                viewModel.storyId.value?.let { storyId -> viewModel.getCommentList(storyId) }
            }
        })

        viewModel.emitCommentList.observe(viewLifecycleOwner, {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                scentCommentAdapter.submitData(PagingData.empty())
                viewModel.pagingCommentList.collectLatest {
                    scentCommentAdapter.submitData(it)
                }
            }
        })
    }

    private fun setupResizeScrollView() {
        binding.commentRecyclerView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom) {
                binding.commentScrollView.updateLayoutParams { height = 510.dp }
                binding.commentFilterView.isVisible = false
            } else {
                binding.commentScrollView.updateLayoutParams { height = 260.dp }
                binding.commentFilterView.isVisible = true
                binding.addCommentEditText.clearFocus()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (requireView().parent as? View)?.let { BottomSheetBehavior.from(it) }?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isDraggable = false
        }
    }

    override fun onReplyClick(comment: Comment) {
        viewModel.setCommentData(comment)
        val bottomSheetDialog = ScentReplyBottomSheetFragment()
        bottomSheetDialog.show(parentFragmentManager, "")
    }
}