package com.task.ui.component.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.data.dto.trade.DataItem
import com.task.data.dto.trade.TradeItems
import com.task.databinding.RecipeItemBinding
import com.task.ui.base.listeners.RecyclerItemListener
import com.task.ui.component.recipes.RecipesListViewModel

/**
 * Created by Sumeetbhut
 */

class RecipesAdapter(private val recipesListViewModel: RecipesListViewModel, private val recipes: List<DataItem>) : RecyclerView.Adapter<RecipeViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(recipe: DataItem) {
            //recipesListViewModel.openRecipeDetails(recipe)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}

