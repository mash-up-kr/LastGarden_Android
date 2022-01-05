package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.Constant
import com.mashup.lastgarden.Constant.KEY_PERFUME_ID
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentPerfumeRecommendBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class PerfumeRecommendFragment : BaseViewModelFragment(),
    PerfumeRecommendPagingAdapter.OnRecommendItemClickListener {

    private var binding by autoCleared<FragmentPerfumeRecommendBinding>()
    private val viewModel by viewModels<PerfumeRecommendViewModel>()
    private lateinit var adapter: PerfumeRecommendPagingAdapter

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onToolbarSetup(toolbar: Toolbar) {
        toolbar.navigationIcon?.mutate()?.setTint(resources.getColor(R.color.point, null))
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        adapter = PerfumeRecommendPagingAdapter(glideRequests, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfumeRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        binding.recyclerView.adapter = adapter
    }

    override fun onBindViewModelsOnCreate() {
        lifecycleScope.launchWhenCreated {
            viewModel.perfumeRecommendItems
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }

    override fun onPerfumeRecommendClick(id: String) {
        findNavController().navigate(
            R.id.perfumeDetailFragment,
            bundleOf(KEY_PERFUME_ID to id)
        )
    }
}