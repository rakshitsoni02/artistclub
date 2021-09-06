package com.dice.artistoverview.screens.view

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dice.artistoverview.R
import com.dice.artistoverview.vo.AlbumUIModel
import com.dice.artistoverview.databinding.AlbumOverviewItemBinding

internal class AlbumArticlesAdapter :
    ListAdapter<AlbumUIModel, AlbumArticlesAdapter.AlbumItemHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AlbumItemHolder(
            AlbumOverviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(albumItemHolder: AlbumItemHolder, position: Int) {
        albumItemHolder.bind(
            getItem(position)
        )
    }

    class AlbumItemHolder(
        private val binding: AlbumOverviewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: AlbumUIModel) {
            binding.apply {
                albumTitle.text = album.title
                albumDesc.text = TextUtils.concat(
                    binding.root.context.getString(R.string.album_release, album.releases.size),
                    " ",
                    binding.root.context.getString(R.string.article_score, album.score)
                )
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AlbumUIModel>() {
            override fun areItemsTheSame(oldItem: AlbumUIModel, newItem: AlbumUIModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: AlbumUIModel,
                newItem: AlbumUIModel
            ): Boolean = oldItem == newItem
        }
    }
}
