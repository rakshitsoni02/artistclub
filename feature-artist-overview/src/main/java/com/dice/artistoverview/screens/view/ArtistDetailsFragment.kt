package com.dice.artistoverview.screens.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.dice.artistoverview.R
import com.dice.artistoverview.vo.ArtistDetails
import com.dice.artistoverview.databinding.ArtistDetailsFragmentBinding
import com.dice.artistoverview.screens.viewmodel.ArtistDetailsViewModel
import com.dice.shared.base.ui.BaseFragment
import com.dice.shared.lifecycle.ResultState
import com.dice.shared.utils.view.RecyclerViewPagination
import com.dice.shared.utils.view.launchAndRepeatWithViewLifecycle
import com.dice.shared.utils.view.toast
import kotlinx.coroutines.flow.collect

internal class ArtistDetailsFragment : BaseFragment() {

    val viewModel: ArtistDetailsViewModel by viewModels()

    private var _binding: ArtistDetailsFragmentBinding? = null

    private val binding: ArtistDetailsFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArtistDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        baseActivity?.onBackPressed()
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        val albumArticlesAdapter = AlbumArticlesAdapter()
        binding.albumsRecyclerView.apply {
            adapter = albumArticlesAdapter
            RecyclerViewPagination(
                this,
                isLoading = {
                    viewModel.isLoading
                },
                loadMore = {
                    viewModel.onAlbumsReachedBottom()
                },
                onLast = {
                    viewModel.isLimitReached
                }
            )
        }
        launchAndRepeatWithViewLifecycle {
            viewModel.artistDetailsResult.collect {
                when (it) {
                    is ResultState.Success -> {
                        bindArtist(it.data)
                        bindAlbum(it.data, albumArticlesAdapter)
                    }
                    is ResultState.Error -> {
                        toast(getString(R.string.api_error_label))
                    }
                    is ResultState.Loading -> {
                        //loading view can be set here
                    }
                }
            }
        }
        handleRecyclerViewState(albumArticlesAdapter)
    }

    private fun refreshViewState(itemCount: Int) {
        binding.apply {
            when (itemCount) {
                0 -> {
                    binding.fArtistAlbumEmptyView.isVisible = true
                    binding.albumsRecyclerView.isVisible = false
                }
                else -> {
                    binding.fArtistAlbumEmptyView.isVisible = false
                    binding.albumsRecyclerView.isVisible = true
                }
            }
        }
    }

    private fun handleRecyclerViewState(adapter: AlbumArticlesAdapter) {
        val itemCount = adapter.itemCount
        adapter.registerAdapterDataObserver(
            object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() = refreshViewState(itemCount)
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) =
                    refreshViewState(itemCount)

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) =
                    refreshViewState(itemCount)
            }
        )
    }

    private fun bindAlbum(data: ArtistDetails, albumArticlesAdapter: AlbumArticlesAdapter) {
        albumArticlesAdapter.submitList(data.albums)
    }

    private fun bindArtist(data: ArtistDetails) {
        binding.artistName.text = data.artist.name
        binding.artistDescription.text = TextUtils.concat(
            getString(R.string.search_artist_country, data.artist.country.text(context)),
            " ",
            getString(R.string.article_score, data.artist.score),
        )
    }

    companion object {
        const val ARGS_ARTIST_ID = "ARGS_ARTIST_ID"
        fun newInstance(orderId: String) = ArtistDetailsFragment().apply {
            arguments = bundleOf(ARGS_ARTIST_ID to orderId)
        }
    }
}