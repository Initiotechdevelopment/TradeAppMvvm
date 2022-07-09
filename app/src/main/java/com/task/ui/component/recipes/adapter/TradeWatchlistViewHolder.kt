package com.task.ui.component.recipes.adapter

import androidx.recyclerview.widget.RecyclerView
import com.task.data.dto.trade.DataItem
import com.task.data.dto.trade.TradeItems
import com.task.data.dto.trade.Watchlistname
import com.task.databinding.TradeItemBinding
import com.task.databinding.TradeWatchlistBinding
import com.task.ui.base.listeners.RecyclerItemListener

/**
 * Created by Sumeetbhut
 */

interface TradeAdapterItemlistioner {
    fun onItemSelected(recipe: Watchlistname)
}

class TradeWatchlistViewHolder(private val itemBinding: TradeWatchlistBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipesItem: Watchlistname, recyclerItemListener: TradeAdapterItemlistioner) {
        itemBinding.textwatchlist.text = recipesItem.mwatchName
        itemBinding.rlRecipeItem.setOnClickListener {
            recyclerItemListener.onItemSelected(recipesItem)
        }
    }
}

