package com.task.ui.component.recipes.adapter

import androidx.recyclerview.widget.RecyclerView
import com.task.data.dto.trade.DataItem
import com.task.data.dto.trade.TradeItems
import com.task.databinding.TradeItemBinding
import com.task.ui.base.listeners.RecyclerItemListener
import kotlin.random.Random

/**
 * Created by Sumeetbhut
 */

class RecipeViewHolder(private val itemBinding: TradeItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipesItem: DataItem, recyclerItemListener: RecyclerItemListener) {
        itemBinding.textViewTitle.text = recipesItem.name
        itemBinding.textViewExchExchType.text = recipesItem.exch+"|" + recipesItem.exchType
        itemBinding.textViewTime.text = recipesItem.lastTradeTime
        itemBinding.textViewLastTradePrice.text = (recipesItem.lastTradePrice).toString();
       /* if(recipesItem.lastTradePrice.compareTo()){

        }*/
        itemBinding.textViewPClose.text = recipesItem.pClose.toString()
        itemBinding.textViewVolume.text = recipesItem.volume.toString()
//        Picasso.get().load(recipesItem.thumb).placeholder(R.drawable.ic_healthy_food).error(R.drawable.ic_healthy_food).into(itemBinding.ivRecipeItemImage)
        itemBinding.rlRecipeItem.setOnClickListener { recyclerItemListener.onItemSelected(recipesItem) }
    }
}



