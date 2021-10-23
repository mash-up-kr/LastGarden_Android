package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.FragmentScentListBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@AndroidEntryPoint
class ScentListFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentScentListBinding>()

    private val viewModel by viewModels<PerfumeDetailViewModel>()

    private lateinit var perfumeDetailAdapter: PerfumeDetailAdapter

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        perfumeDetailAdapter = PerfumeDetailAdapter(glideRequests)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScentListBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        initRecyclerView()
    }

    override fun onBindViewModelsOnCreate() {
        lifecycleScope.launchWhenCreated {
            viewModel.perfumeDetailItems
                .filterNotNull()
                .collectLatest {
                    perfumeDetailAdapter.submitList(it)
                }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = perfumeDetailAdapter
    }
}