package com.mashup.lastgarden.ui.scent.comment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.base.autoCleared
import com.mashup.base.extensions.hideSoftInput
import com.mashup.base.image.GlideRequests
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
    private val viewModel: ScentCommentViewModel by viewModels()

    private lateinit var scentCommentAdapter: ScentCommentPagingAdapter

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
        scentCommentAdapter = ScentCommentPagingAdapter(glideRequests, this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as? BottomSheetDialog
            if (bottomSheetDialog != null) {
                setupBottomSheet(bottomSheetDialog)
            }
        }
        return dialog
    }

    private fun setupBottomSheet(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(R.id.commentBottomSheet) as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScentCommentBottomSheetBinding.inflate(inflater, container, false)

        setupViews()
        onBindViewModels()

        return binding.root
    }

    private fun setupViews() {
        binding.commentRecyclerView.adapter = scentCommentAdapter

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.applyButton.setOnClickListener {
            val contents = binding.addCommentEditText.text.toString()
            viewModel.addComment(contents)
        }

        binding.commentRecyclerView.addOnItemTouchListener(object :
            RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                binding.addCommentEditText.hideSoftInput()
                binding.addCommentEditText.clearFocus()
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })

        binding.addCommentEditText.setOnFocusChangeListener { _, hasFocus ->
            binding.commentFilterView.isVisible = !hasFocus
        }
    }

    private fun onBindViewModels() {
        viewModel.addCommentSuccess.observe(viewLifecycleOwner, {
            if (it == ScentCommentViewModel.AddCommentState.SUCCESS) {
                binding.addCommentEditText.text.clear()
                binding.addCommentEditText.hideSoftInput()
                binding.addCommentEditText.clearFocus()
            }
        })

        viewModel.emittedCommentList.observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenCreated {
                scentCommentAdapter.submitData(PagingData.empty())
                viewModel.pagingCommentList.collectLatest {
                    scentCommentAdapter.submitData(it)
                }
            }
        })
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
        bottomSheetDialog.show(parentFragmentManager, null)
    }
}