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
import com.mashup.lastgarden.data.vo.Comment
import com.mashup.lastgarden.databinding.ScentCommentBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScentCommentBottomSheetFragment : BottomSheetDialogFragment(),
    ScentCommentAdapter.OnClickListener {
    private var binding by autoCleared<ScentCommentBottomSheetBinding>()
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
        binding = ScentCommentBottomSheetBinding.inflate(inflater, container, false)
        binding.commentScrollView.updateLayoutParams { height = 510.dp }
        setupResizeScrollView()

        viewModel.getCommentList()
        //FIXME comment, reply adapter랑 데이터 하나로 합치기
        viewModel.commentList.observe(this, {
            binding.commentRecyclerView.adapter = ScentCommentAdapter(it, this)
        })

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        return binding.root
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