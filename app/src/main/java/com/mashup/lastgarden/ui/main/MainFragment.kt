package com.mashup.lastgarden.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentMainBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.upload.editor.EditorActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseViewModelFragment(), MainAdapter.OnMainItemClickListener {

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
        binding.toolbar.title = ""

        binding.recyclerView.adapter = adapter
        binding.floatingButton.setOnClickListener {
            moveEditorActivity()
        }
    }

    override fun onBindViewModelsOnCreate() {
        lifecycleScope.launchWhenCreated {
            viewModel.mainItems
                .filterNotNull()
                .collectLatest {
                    adapter.submitList(it)
                }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.todayPerfumeStories
                .filterNotNull()
                .collectLatest {
                    todayPerfumeStoryAdapter.submitList(it)
                }
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

    private fun moveEditorActivity() {
        requireActivity().run {
            startActivity(
                Intent(requireContext(), EditorActivity::class.java)
            )
        }
    }

    override fun onBannerClick() {
        startActivity(Intent(requireActivity(), EditorActivity::class.java))
        viewModel.setIsShowBanner(false)
    }

    override fun onSeeMoreClick() {
        findNavController().navigate(R.id.perfumeRecommendFragment)
    }
}