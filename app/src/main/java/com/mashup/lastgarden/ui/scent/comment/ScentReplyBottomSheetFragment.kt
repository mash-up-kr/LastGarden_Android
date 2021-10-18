package com.mashup.lastgarden.ui.scent.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.base.autoCleared
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
    ): View? {
        binding = ScentReplyBottomSheetBinding.inflate(inflater, container, false)
        resizeViewHeight(binding.replyScrollView, 380f)
        setupResizeScrollView()

        viewModel.getReplyList()
        viewModel.replyList.observe(this, {
            binding.replyRecyclerView.adapter = ScentReplyAdapter(it)
        })

        viewModel.commentDetail.observe(this, {
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
                resizeViewHeight(binding.replyScrollView, 130f)
                binding.commentFilterView.visibility = View.GONE
            },
            onHideKeyboard = {
                resizeViewHeight(binding.replyScrollView, 380f)
                binding.addCommentEditText.clearFocus()
                binding.commentFilterView.visibility = View.VISIBLE
            })
    }

    private fun resizeViewHeight(view: View, height: Float) {
        val deviceHeight = requireActivity().resources.displayMetrics.density
        val layoutParams = view.layoutParams
        layoutParams.height = (deviceHeight * height).toInt()
        view.layoutParams = layoutParams
    }

    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }
}