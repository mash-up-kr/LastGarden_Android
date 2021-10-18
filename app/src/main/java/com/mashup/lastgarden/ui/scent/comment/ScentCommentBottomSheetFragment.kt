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
import com.mashup.lastgarden.data.vo.Comment
import com.mashup.lastgarden.databinding.ScentCommentBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScentCommentBottomSheetFragment : BottomSheetDialogFragment(),
    ScentCommentAdapter.OnClickListener {
    private var binding by autoCleared<ScentCommentBottomSheetBinding>()
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
        binding = ScentCommentBottomSheetBinding.inflate(inflater, container, false)
        resizeViewHeight(binding.commentScrollView, 510f)
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

    //FIXME bottomsheet 높이 조절방법 바꾸기
    private fun setupResizeScrollView() {
        keyboardVisibilityUtils = KeyboardVisibilityUtils(requireActivity().window,
            onShowKeyboard = {
                resizeViewHeight(binding.commentScrollView, 260f)
                binding.commentFilterView.visibility = View.GONE
            },
            onHideKeyboard = {
                resizeViewHeight(binding.commentScrollView, 510f)
                binding.addCommentEditText.clearFocus()
                binding.commentFilterView.visibility = View.VISIBLE
            })
    }

    //FIXME 한개로 빼기
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

    override fun onReplyClick(comment: Comment) {
        viewModel._commentDetail.value = comment
        val bottomSheetDialog = ScentReplyBottomSheetFragment()
        bottomSheetDialog.show(parentFragmentManager, "")
    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }
}