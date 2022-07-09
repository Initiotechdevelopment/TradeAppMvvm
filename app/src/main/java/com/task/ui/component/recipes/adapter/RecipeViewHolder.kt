package com.task.ui.component.recipes.adapter

import androidx.recyclerview.widget.RecyclerView
import com.task.data.dto.trade.DataItem
import com.task.data.dto.trade.TradeItems
import com.task.databinding.RecipeItemBinding
import com.task.ui.base.listeners.RecyclerItemListener

/**
 * Created by Sumeetbhut
 */

class RecipeViewHolder(private val itemBinding: RecipeItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipesItem: DataItem, recyclerItemListener: RecyclerItemListener) {
        itemBinding.tvCaption.text = recipesItem.exch
        itemBinding.tvName.text = recipesItem.name
//        Picasso.get().load(recipesItem.thumb).placeholder(R.drawable.ic_healthy_food).error(R.drawable.ic_healthy_food).into(itemBinding.ivRecipeItemImage)
        itemBinding.rlRecipeItem.setOnClickListener { recyclerItemListener.onItemSelected(recipesItem) }
    }
}

