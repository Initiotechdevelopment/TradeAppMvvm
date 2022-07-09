package com.task.ui.component.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.data.dto.trade.DataItem
import com.task.data.dto.trade.TradeItems
import com.task.data.dto.trade.Watchlistname
import com.task.databinding.RecipeItemBinding
import com.task.databinding.TradeItemBinding
import com.task.databinding.TradeWatchlistBinding
import com.task.ui.base.listeners.RecyclerItemListener
import com.task.ui.component.recipes.RecipesListViewModel

/**
 * Created by Sumeetbhut
 */

class TradeWatchlistAdapter(
    private val recipesListViewModel: RecipesListViewModel,
    private val recipes: List<Watchlistname>
) : RecyclerView.Adapter<TradeWatchlistViewHolder>() {

    private val onItemClickListener: TradeAdapterItemlistioner =
        object : TradeAdapterItemlistioner {

            override fun onItemSelected(recipe: Watchlistname) {
                recipesListViewModel.selectedTab = recipe.mwatchName.toString()
                recipesListViewModel.getJsonDataFromAssetWithSelectedTab()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeWatchlistViewHolder {
        val itemBinding =
            TradeWatchlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TradeWatchlistViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TradeWatchlistViewHolder, position: Int) {
        holder.bind(recipes[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}

