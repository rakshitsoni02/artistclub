package com.dice.shared.utils.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerViewPagination(
    recyclerView: RecyclerView,
    private val isLoading: () -> Boolean,
    private val loadMore: (Int) -> Unit,
    private val onLast: () -> Boolean = { true }
) : RecyclerView.OnScrollListener() {

    private var threshold: Int = PAGINATION_THRESH_HOLD
    private var currentPage: Int = PAGINATION_START
    private var endWithAuto = true

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.layoutManager?.let {
            val visibleItemCount = it.childCount
            val totalItemCount = it.itemCount
            val lastVisibleItemPosition = when (it) {
                is LinearLayoutManager -> it.findLastVisibleItemPosition()
                is GridLayoutManager -> it.findLastVisibleItemPosition()
                is StaggeredGridLayoutManager -> findLastVisibleItemPosition(
                    it.findLastVisibleItemPositions(
                        null
                    )
                )
                else -> return
            }

            if (onLast() || isLoading()) return

            if (endWithAuto) {
                if (visibleItemCount + lastVisibleItemPosition == totalItemCount) return
            }

            if ((visibleItemCount + lastVisibleItemPosition + threshold) >= totalItemCount) {
                loadMore(++currentPage)
            }
        }
    }

    private fun findLastVisibleItemPosition(lastVisibleItems: IntArray): Int {
        return lastVisibleItems.maxOfOrNull { it } ?: lastVisibleItems[0]
    }

    companion object {
        private const val PAGINATION_THRESH_HOLD = 8
        private const val PAGINATION_START = 1
    }
}
