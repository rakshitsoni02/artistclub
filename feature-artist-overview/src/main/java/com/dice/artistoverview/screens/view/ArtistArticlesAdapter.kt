package com.dice.artistoverview.screens.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dice.artistoverview.R
import com.dice.artistoverview.vo.ArtistUIModel
import com.dice.artistoverview.databinding.ArtistOverviewItemBinding

internal class ArtistArticlesAdapter(private val clickHandler: (ArtistUIModel) -> Unit) :
    ListAdapter<ArtistUIModel, ArtistArticlesAdapter.ArtistItemHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArtistItemHolder(
            ArtistOverviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(artistItemHolder: ArtistItemHolder, position: Int) {
        artistItemHolder.bind(
            getItem(position),
            clickHandler
        )
    }

    class ArtistItemHolder(
        private val binding: ArtistOverviewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: ArtistUIModel, clickHandler: (ArtistUIModel) -> Unit) {
            binding.apply {
                itemView.setOnClickListener {
                    clickHandler(artist)
                }
                artistName.text = artist.name
                artistCountry.text = root.context.getString(
                    R.string.search_artist_country,
                    artist.country.text(root.context)
                )
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArtistUIModel>() {
            override fun areItemsTheSame(oldItem: ArtistUIModel, newItem: ArtistUIModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArtistUIModel,
                newItem: ArtistUIModel
            ): Boolean = oldItem == newItem
        }
    }
}
