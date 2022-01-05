package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentScentListBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ScentListFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentScentListBinding>()

    private val viewModel by viewModels<PerfumeDetailViewModel>(
        ownerProducer = { parentFragment ?: this }
    )

    private lateinit var perfumeDetailAdapter: PerfumeDetailPagingAdapter

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        perfumeDetailAdapter = PerfumeDetailPagingAdapter(glideRequests)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        initRecyclerView()
        addListener()
    }

    private fun initRecyclerView() {
        binding.scentListRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.scentListRecyclerView.adapter = perfumeDetailAdapter
    }

    private fun addListener() {
        perfumeDetailAdapter.setItemClickListener(object :
            PerfumeDetailPagingAdapter.OnItemClickListener {
            override fun onStoryItemClick(storyId: Int, storyIndex: Int) {
                findNavController().navigate(
                    R.id.actionPerfumeDetailFragmentToScentFragment,
                    bundleOf("storyId" to storyId)
                )
            }
        })
    }

    override fun onBindViewModelsOnViewCreated() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.items.collectLatest {
                perfumeDetailAdapter.submitData(it)
            }
        }
    }
}