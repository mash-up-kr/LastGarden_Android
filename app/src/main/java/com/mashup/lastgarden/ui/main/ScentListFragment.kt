package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.data.vo.PerfumeDetailData
import com.mashup.lastgarden.databinding.FragmentScentListBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScentListFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentScentListBinding>()
    private lateinit var perfumeDetailAdapter: PerfumeDetailAdapter

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScentListBinding.inflate(
            inflater, container, false
        )
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        perfumeDetailAdapter = PerfumeDetailAdapter(glideRequests)
        perfumeDetailAdapter.data = mutableListOf(
            PerfumeDetailData(
                "https://images.unsplash.com/photo-1633178082360-4f2b133c7399?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80",
                "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                "seehyang",
                "#플로럴 #우디 #머스크",
                200
            ), PerfumeDetailData(
                "https://images.unsplash.com/photo-1633090332452-532d6b39422a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=2516&q=80",
                "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                "seehyang",
                "#머스크",
                127
            ), PerfumeDetailData(
                "https://images.unsplash.com/photo-1633074320366-365b5e382fb5?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1374&q=80",
                "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                "seehyang",
                "#시트러스",
                325
            ), PerfumeDetailData(
                "https://images.unsplash.com/photo-1633124890681-fc9afca4c4ac?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80",
                "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                "seehyang",
                "#우디 #플로럴",
                601
            ), PerfumeDetailData(
                "https://images.unsplash.com/photo-1633131902932-31ba72ee19d7?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1470&q=80",
                "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                "seehyang",
                "#플로럴 #머스크 #우디",
                125
            ), PerfumeDetailData(
                "https://images.unsplash.com/photo-1633166158652-22faaa722751?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80",
                "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                "seehyang",
                "#플로럴",
                100
            )
        )
        binding.recyclerView.adapter = perfumeDetailAdapter
        perfumeDetailAdapter.notifyDataSetChanged()
    }
}