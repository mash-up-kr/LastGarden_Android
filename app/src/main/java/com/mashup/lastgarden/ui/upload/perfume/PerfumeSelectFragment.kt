package com.mashup.lastgarden.ui.upload.perfume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentPerfumeSearchBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.upload.UploadViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PerfumeSelectFragment : BaseViewModelFragment(), PerfumeSelectAdapter.OnPerfumeClickListener {

    private var binding by autoCleared<FragmentPerfumeSearchBinding>()
    private val viewModel by activityViewModels<UploadViewModel>()

    lateinit var perfumeSelectAdapter: PerfumeSelectAdapter

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        perfumeSelectAdapter = PerfumeSelectAdapter(
            glideRequests, this@PerfumeSelectFragment
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfumeSearchBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)

        setUiOfToolBar()
        setUiOfEditText()
        setUiOfButton()
        setUiOfPerfumeRecyclerView()
    }

    override fun onBindViewModelsOnViewCreated() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.queryOfPerfume.collectLatest { query ->
                binding.titleSearchingResult.text =
                    getString(R.string.result_title_searching, query)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedPerfume.collectLatest { perfume ->
                binding.bottomButtonLayout.isVisible = perfume is PerfumeItem.PerfumeSearchedItem
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.searchedPerfumeList
                .collectLatest {
                    perfumeSelectAdapter.submitData(it)
                }
        }
    }

    private fun setUiOfToolBar() {
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun setUiOfButton() {
        binding.confirmButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUiOfEditText() {
        binding.searchingEditText.setOnSearchButtonClick { nameOfPerfume ->
            viewModel.requestPerfumeWithName(nameOfPerfume)
        }
    }

    private fun setUiOfPerfumeRecyclerView() {
        (binding.perfumeRecyclerView.root).apply {
            adapter = perfumeSelectAdapter
        }
    }

    override fun onPerfumeClick(perfume: PerfumeItem.PerfumeSearchedItem) {
        viewModel.updateSelectedPerfume(perfume)
    }
}