package com.mashup.lastgarden.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentMainBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.scent.MainStorySet
import com.mashup.lastgarden.ui.upload.editor.EditorActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseViewModelFragment(), MainAdapter.OnMainItemClickListener, TodayPerfumeStoryAdapter.OnTodayPerfumeStoryClickListener {

    private var binding by autoCleared<FragmentMainBinding>()

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var glideRequests: GlideRequests

    @Inject
    lateinit var todayPerfumeStoryAdapter: TodayPerfumeStoryAdapter

    @Inject
    lateinit var hotStoryAdapter: HotStoryAdapter

    @Inject
    lateinit var rankingAdapter: PerfumeRankingAdapter

    @Inject
    lateinit var recommendAdapter: PerfumeRecommendAdapter

    private lateinit var adapter: MainAdapter

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        adapter = MainAdapter(
            glideRequests = glideRequests,
            todayPerfumeStoryAdapter = todayPerfumeStoryAdapter,
            hotStoryAdapter = hotStoryAdapter,
            rankingAdapter = rankingAdapter,
            recommendAdapter = recommendAdapter,
            mainItemClickListener = this
        )
        todayPerfumeStoryAdapter.setOnTodayPerfumeStoryClickListener(this)
        hotStoryAdapter.setOnTodayPerfumeStoryClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        binding.recyclerView.adapter = adapter

        binding.floatingButton.setOnClickListener {
            moveEditorActivity()
        }
    }

    override fun onBindViewModelsOnCreate() {
        lifecycleScope.launchWhenCreated {
            viewModel.mainItems
                .filterNotNull()
                .collectLatest { adapter.submitList(it) }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.todayPerfumeStories
                .filterNotNull()
                .collectLatest { todayPerfumeStoryAdapter.submitList(it) }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.hotStories
                .filterNotNull()
                .collectLatest { hotStoryAdapter.submitList(it) }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.rankingsItem
                .filterNotNull()
                .collectLatest { rankingAdapter.submitList(it) }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.recommendsItem
                .filterNotNull()
                .collectLatest { recommendAdapter.submitList(it) }
        }
    }

    override fun onRefreshPerfumeClick() {
        viewModel.refreshTodayPerfume()
    }

    override fun onBannerClick() {
        startActivity(Intent(requireActivity(), EditorActivity::class.java))
        viewModel.setIsShowBanner(false)
    }

    override fun onSeeMoreClick() {
        findNavController().navigate(R.id.perfumeRecommendFragment)
    }

    private fun moveEditorActivity() {
        requireActivity().run {
            startActivity(
                Intent(requireContext(), EditorActivity::class.java)
            )
        }
    }

    override fun todayPerfumeStoryClick(position:Int) {
        viewModel.getMainStorySet()
        val todayMainSet = viewModel.perfumeStorySet.value
        findNavController().navigate(R.id.mainFragment)
        findNavController().navigate(
            R.id.actionMainFragmentToScentFragment,
            bundleOf(
                "mainStorySet" to todayMainSet, "storyPosition" to position
            )
        )
    }

    override fun hotPerfumeStoryClick(position: Int) {
        viewModel.getHotStorySet()
        val hotStorySet = viewModel.perfumeStorySet.value
        findNavController().navigate(R.id.mainFragment)
        findNavController().navigate(
            R.id.actionMainFragmentToScentFragment,
            bundleOf(
                "mainStorySet" to hotStorySet, "storyPosition" to position
            )
        )
    }


}