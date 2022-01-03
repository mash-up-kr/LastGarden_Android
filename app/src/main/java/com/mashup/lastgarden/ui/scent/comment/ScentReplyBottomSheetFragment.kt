package com.mashup.lastgarden.ui.scent.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.base.autoCleared
import com.mashup.base.utils.dp
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ScentReplyBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScentReplyBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding by autoCleared<ScentReplyBottomSheetBinding>()
    private val viewModel: ScentCommentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScentReplyBottomSheetBinding.inflate(inflater, container, false)
        binding.replyScrollView.updateLayoutParams { height = 380.dp }
        setupResizeScrollView()

        viewModel.getReplyList()
        viewModel.replyList.observe(viewLifecycleOwner, {
            binding.replyRecyclerView.adapter = ScentReplyAdapter(it)
        })

        viewModel.commentDetail.observe(viewLifecycleOwner, {
            binding.includeDetailLayout.nicknameTextView.text = it.userNickname
            binding.includeDetailLayout.dateTextView.text = it.createdAt
            binding.includeDetailLayout.contentTextView.text = it.contents
            binding.includeDetailLayout.replyCountTextView.text =
                binding.includeDetailLayout.replyCountTextView.resources.getString(
                    R.string.reply_count,
                    it.replyCount
                )
            binding.includeDetailLayout.commentLikeButton.text = it.likeCount.toString()
            binding.includeDetailLayout.commentDislikeButton.text = it.dislikeCount.toString()
//            btnThumbsUpDownSelector(
//                binding.includeDetailLayout.commentLikeButton,
//                binding.includeDetailLayout.commentDislikeButton,
//                it.likeState,
//                requireContext()
//            )
        })

        binding.closeButton.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun setupResizeScrollView() {
        binding.replyScrollView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom) {
                binding.replyScrollView.updateLayoutParams { height = 380.dp }
                binding.commentFilterView.isVisible = false
            } else {
                binding.replyScrollView.updateLayoutParams { height = 130.dp }
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
}