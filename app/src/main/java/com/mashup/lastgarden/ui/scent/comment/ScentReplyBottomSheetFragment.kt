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
import com.mashup.lastgarden.extensions.btnThumbsUpDownSelector
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScentReplyBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding by autoCleared<ScentReplyBottomSheetBinding>()
    private val viewModel: ScentCommentViewModel by activityViewModels()

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

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
            binding.includeDetailLayout.nicknameTextView.text = it.nickName
            binding.includeDetailLayout.dateTextView.text = it.date
            binding.includeDetailLayout.contentTextView.text = it.content
            binding.includeDetailLayout.replyCountTextView.text = "답글 " + it.replyCount.toString()
            binding.includeDetailLayout.commentLikeButton.text = it.likeCount.toString()
            binding.includeDetailLayout.commentDislikeButton.text = it.dislikeCount.toString()
            btnThumbsUpDownSelector(
                binding.includeDetailLayout.commentLikeButton,
                binding.includeDetailLayout.commentDislikeButton,
                it.likeState,
                requireContext()
            )
        })

        binding.closeButton.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun setupResizeScrollView() {
        keyboardVisibilityUtils = KeyboardVisibilityUtils(requireActivity().window,
            onShowKeyboard = {
                binding.replyScrollView.updateLayoutParams { height = 130.dp }
                binding.commentFilterView.isVisible = false
            },
            onHideKeyboard = {
                binding.replyScrollView.updateLayoutParams { height = 380.dp }
                binding.addCommentEditText.clearFocus()
                binding.commentFilterView.isVisible = true
            })
    }

    override fun onStart() {
        super.onStart()
        (requireView().parent as? View)?.let { BottomSheetBehavior.from(it) }?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isDraggable = false
        }
    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }
}