package com.task.ui.base.listeners

import com.task.data.dto.recipes.RecipesItem
import com.task.data.dto.trade.DataItem
import com.task.data.dto.trade.TradeItems

/**
 * Created by Sumeetbhut
 */

interface RecyclerItemListener {
    fun onItemSelected(recipe: DataItem)
}
