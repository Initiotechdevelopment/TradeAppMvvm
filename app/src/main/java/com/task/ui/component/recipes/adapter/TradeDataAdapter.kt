package com.task.ui.component.recipes.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.task.R
import com.task.data.dto.trade.DataItem
import com.task.databinding.TradeItemBinding
import com.task.ui.base.listeners.RecyclerItemListener
import com.task.ui.component.recipes.RecipesListViewModel
import kotlinx.android.synthetic.main.bottom_sheet_layout.*

/**
 * Created by Sumeetbhut
 */

class   TradeDataAdapter(
    private val recipesListViewModel: RecipesListViewModel,
    private val recipes: List<DataItem>
) : RecyclerView.Adapter<RecipeViewHolder>() {

    private lateinit var activity: Activity

    fun setActivity(act: Activity) {
        this.activity = act;
    }

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(recipe: DataItem) {
            //recipesListViewModel.openRecipeDetails(recipe)
            openDataBottomSheet(activity,recipe)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding =
            TradeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    private fun openDataBottomSheet(contaxt: Context, dateItem: DataItem) {
        val dialog = BottomSheetDialog(contaxt)

        // on below line we are inflating a layout file which we have created.
        val view = (contaxt as Activity).layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        dialog.setCancelable(true)

        // on below line we are setting
        // content view to our view.
        dialog.setContentView(view)

        dialog.textViewDayHigh.text = dateItem.dayHigh.toString()
        dialog.textViewDayLow.text = dateItem.dayLow.toString()
        dialog.textViewNseBseCode.text = dateItem.nseBseCode.toString()
        dialog.textViewScripCode.text = dateItem.scripCode.toString()

        // on below line we are calling
        // a show method to display a dialog.
        dialog.show()
    }
}

