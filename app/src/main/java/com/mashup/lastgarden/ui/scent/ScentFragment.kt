package com.mashup.lastgarden.ui.scent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentScentBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.scent.comment.ScentCommentBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScentFragment : BaseViewModelFragment(), ScentViewPagerAdapter.OnClickListener {
    private var binding by autoCleared<FragmentScentBinding>()
    private val viewModel: ScentViewModel by activityViewModels()

    @Inject
    lateinit var glideRequests: GlideRequests

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
        binding.closeButton.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setupBottomSheet() {
        binding.sortButton.setOnClickListener {
            val bottomSheetDialog = ScentSortBottomSheetFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "")
        }
    }

    override fun onBindViewModelsOnViewCreated() {
        super.onBindViewModelsOnViewCreated()

        //TODO 분기처리해서 함수호출
        viewModel.getPerfumeStoryList(1)
        viewModel.perfumeStoryList.observe(viewLifecycleOwner) {
            binding.scentViewPager.adapter = ScentViewPagerAdapter(it, glideRequests, this)
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

    override fun onCommentClick(scentId: Int) {
        val bottomSheetDialog = ScentCommentBottomSheetFragment()
        bottomSheetDialog.show(requireActivity().supportFragmentManager, "")
    }

    override fun onLikeClick(scentId: Int) {
        viewModel.getPerfumeStoryLike(scentId)
    }

}