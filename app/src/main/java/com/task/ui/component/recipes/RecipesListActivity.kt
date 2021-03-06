package com.task.ui.component.recipes

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

import com.task.R
import com.task.RECIPE_ITEM_KEY
import com.task.data.Resource
import com.task.data.dto.trade.*
import com.task.data.error.SEARCH_ERROR
import com.task.databinding.HomeActivityBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.details.DetailsActivity
import com.task.ui.component.recipes.adapter.TradeDataAdapter
import com.task.ui.component.recipes.adapter.TradeWatchlistAdapter
import com.task.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

/**
 * Created by Sumeetbhut
 */
@AndroidEntryPoint
class RecipesListActivity : BaseActivity() {
    private lateinit var binding: HomeActivityBinding

    private val recipesListViewModel: RecipesListViewModel by viewModels()

    private lateinit var tradeDataAdapter: TradeDataAdapter
    private lateinit var tradeWatchlistAdapter: TradeWatchlistAdapter

    override fun initViewBinding() {
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.recipe)
        val layoutManager = LinearLayoutManager(this)
        binding.rvRecipesList.layoutManager = layoutManager

        binding.watchlistname.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)



        binding.rvRecipesList.setHasFixedSize(true)

        recipesListViewModel.set(this)

        recipesListViewModel.getWatchlistname()
        recipesListViewModel.getJsonDataFromAssetWithSelectedTab()


        /* fixedRateTimer("timer", false, 0L, 3 * 1000) {
             this@RecipesListActivity.runOnUiThread {
                 recipesListViewModel.getJsonDataFromAssetWithSelectedTab()
                 Log.d("Datacall","Datacall"+recipesListViewModel.recipes.data?.size)
             }
         }*/

//        binding.watchlistname.findViewHolderForAdapterPosition(0)?.itemView?.performClick()
//        recipesListViewModel.getRecipes()
//        recipesListViewModel.getTradeResponse()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        // Associate searchable configuration with the SearchView
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_by_name)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                handleSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> recipesListViewModel.recipesLiveDataPrivateTradeRes
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleSearch(query: String) {
        if (query.isNotEmpty()) {
            binding.pbLoading.visibility = VISIBLE
            recipesListViewModel.onSearchClick(query)
        }
    }


    private fun bindListData(recipes: List<DataItem>) {
        if (!(recipes.isNullOrEmpty())) {
            tradeDataAdapter = TradeDataAdapter(recipesListViewModel, recipes)
//            tradeDataAdapter
            binding.rvRecipesList.adapter = tradeDataAdapter
            tradeDataAdapter.setActivity(this)

            showDataView(true)
            tradeDataAdapter.notifyDataSetChanged()



        } else {
            showDataView(false)
        }
    }

    private fun bindWatchlist(watchlistname: List<Watchlistname>) {
        if (!(watchlistname.isNullOrEmpty())) {
            tradeWatchlistAdapter = TradeWatchlistAdapter(recipesListViewModel, watchlistname)
            binding.watchlistname.adapter = tradeWatchlistAdapter
            showDataView(true)

        } else {
            showDataView(false)
        }
    }


    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<TradeItems>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(RECIPE_ITEM_KEY, it)
            }
            startActivity(nextScreenIntent)
        }
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        recipesListViewModel.showToastMessage(SEARCH_ERROR)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvRecipesList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvRecipesList.toGone()
    }


    private fun showSearchResult(recipesItem: DataItem) {
        recipesListViewModel.openRecipeDetails(recipesItem)
        binding.pbLoading.toGone()
    }

    private fun noSearchResult(unit: Unit) {
        showSearchError()
        binding.pbLoading.toGone()
    }

    private fun handleRecipesList(status: Resource<TradeResponse>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.data.let { bindListData(recipes = it as List<DataItem>) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { recipesListViewModel.showToastMessage(it) }
            }
        }
    }

    private fun handleTradeList(data: List<DataItem?>) {


        fixedRateTimer("timer1", false, 0L, 3 * 1000) {
            ((this@RecipesListActivity)).runOnUiThread {
                data.get(0)?.lastTradePrice?.plus(Random.nextDouble(10.0))
            }
        }
        data?.let {
            bindListData(recipes = it as List<DataItem>)
        }

        tradeDataAdapter.notifyDataSetChanged()




    }

    override fun observeViewModel() {
//        observe(recipesListViewModel.recipesLiveData, ::handleRecipesList)
        observe(recipesListViewModel.tradeResponse, ::handleTradeList)
        observe(recipesListViewModel.watchlistnameResponse, ::watchlistname)
        //observe(recipesListViewModel.recipeSearchFound, ::showSearchResult)
        observe(recipesListViewModel.noSearchFound, ::noSearchResult)
//        observeEvent(recipesListViewModel.openRecipeDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(recipesListViewModel.showSnackBar)
        observeToast(recipesListViewModel.showToast)

    }

    private fun watchlistname(list: List<Watchlistname?>) {
        list?.let {
            bindWatchlist(watchlistname = it as List<Watchlistname>)
        }

    }

}


