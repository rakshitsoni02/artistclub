package com.dice.artistoverview.screens.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.dice.artistoverview.R
import com.dice.artistoverview.databinding.ArtistOverviewFragmentBinding
import com.dice.artistoverview.screens.viewmodel.ArtistSearchViewModel
import com.dice.shared.base.ui.BaseFragment
import com.dice.shared.lifecycle.ResultState
import com.dice.shared.utils.view.RecyclerViewPagination
import com.dice.shared.utils.view.launchAndRepeatWithViewLifecycle
import com.dice.shared.utils.view.toast
import kotlinx.coroutines.flow.collect

 class ArtistOverViewFragment : BaseFragment() {
    val viewModel: ArtistSearchViewModel by viewModels()

    private var _binding: ArtistOverviewFragmentBinding? = null

    private val binding: ArtistOverviewFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArtistOverviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val artistArticlesAdapter = ArtistArticlesAdapter {
            show(ArtistDetailsFragment.newInstance(it.id), baseActivity)
        }
        binding.fArtistOverviewRecyclerView.apply {
            adapter = artistArticlesAdapter
            RecyclerViewPagination(
                this,
                isLoading = {
                    viewModel.isLoading
                },
                loadMore = {
                    viewModel.onArtistScrolledToBottom()
                },
                onLast = {
                    viewModel.isLastPageReached
                },
            )
        }
        artistArticlesAdapter.apply {
            bindObserver(this)
            handleRecyclerViewState(this)
        }
        binding.fArtistOverviewSearchView.apply {
            setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    dismissKeyboard(binding.fArtistOverviewSearchView)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.onSearchQueryChanged(newText)
                    return true
                }
            })
            setOnQueryTextFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    showKeyboard(view.findFocus())
                }
            }
            requestFocus()
        }
    }

    private fun bindObserver(artistArticlesAdapter: ArtistArticlesAdapter) {
        launchAndRepeatWithViewLifecycle {
            viewModel.searchResults.collect {
                when (it) {
                    is ResultState.Success -> {
                        binding.progress.isVisible = false
                        artistArticlesAdapter.submitList(it.data)
                    }
                    is ResultState.Error -> {
                        binding.progress.isVisible = false
                        toast(getString(R.string.api_error_label))
                    }
                    is ResultState.Loading -> {
                        binding.progress.isVisible = true
                    }
                }
            }
        }
    }

    private fun refreshState(itemCount: Int) {
        binding.apply {
            when (itemCount) {
                0 -> {
                    binding.progress.isVisible = false
                    binding.fArtistOverviewEmptyView.isVisible = true
                    binding.fArtistOverviewRecyclerView.isVisible = false
                }
                else -> {
                    binding.progress.isVisible = false
                    binding.fArtistOverviewEmptyView.isVisible = false
                    binding.fArtistOverviewRecyclerView.isVisible = true
                }
            }
        }
    }

    private fun handleRecyclerViewState(adapter: ArtistArticlesAdapter) {
        val itemCount = adapter.itemCount
        adapter.registerAdapterDataObserver(
            object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() = refreshState(itemCount)
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) =
                    refreshState(itemCount)

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) =
                    refreshState(itemCount)
            }
        )
    }

    override fun onPause() {
        dismissKeyboard(binding.fArtistOverviewSearchView)
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): ArtistOverViewFragment = ArtistOverViewFragment()
    }
}