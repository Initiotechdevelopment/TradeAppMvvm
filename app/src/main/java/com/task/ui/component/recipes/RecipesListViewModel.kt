package com.task.ui.component.recipes

import android.content.Context
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.task.data.DataRepositorySource
import com.task.data.Resource
import com.task.data.dto.recipes.Recipes
import com.task.data.dto.recipes.RecipesItem
import com.task.data.dto.trade.*
import com.task.data.remote.moshiFactories.MyKotlinJsonAdapterFactory
import com.task.data.remote.moshiFactories.MyStandardJsonAdapters
import com.task.ui.base.BaseViewModel
import com.task.utils.SingleEvent
import com.task.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.util.Locale.ROOT
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

/**
 * Created by Sumeetbhut
 */
@HiltViewModel
class RecipesListViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel() {

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
//    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
//    val recipesLiveDataPrivate = MutableLiveData<Resource<TradeResponse>>()
//    val recipesLiveData: LiveData<Resource<TradeResponse>> get() = recipesLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val recipesLiveDataPrivateTradeRes = MutableLiveData<List<DataItem?>>()
    val tradeResponse: LiveData<List<DataItem?>> get() = recipesLiveDataPrivateTradeRes

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val watchlistname = MutableLiveData<List<Watchlistname?>>()
    val watchlistnameResponse: LiveData<List<Watchlistname?>> get() = watchlistname

    //TODO check to make them as one Resource
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val recipeSearchFoundPrivate: MutableLiveData<TradeItems> = MutableLiveData()
    val recipeSearchFound: LiveData<TradeItems> get() = recipeSearchFoundPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val noSearchFoundPrivate: MutableLiveData<Unit> = MutableLiveData()
    val noSearchFound: LiveData<Unit> get() = noSearchFoundPrivate

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val openRecipeDetailsPrivate = MutableLiveData<SingleEvent<RecipesItem>>()
    val openRecipeDetails: LiveData<SingleEvent<RecipesItem>> get() = openRecipeDetailsPrivate

    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    var recipes: TradeResponse = TradeResponse(arrayListOf())

    lateinit var context: Context;

    var selectedTab = "WATCHLIST 1"

    fun set(c:Context){
        this.context = c
    }

    init {
        initData()
    }

 /*   fun getRecipes() {
        viewModelScope.launch {

//            recipesLiveDataPrivate.value = Resource.Loading()

            wrapEspressoIdlingResource {
                dataRepositoryRepository.requestRecipes().collect {
//                    recipesLiveDataPrivate.value = it
                }
            }

        }
    }
*/
    fun getTradeResponse() {
        viewModelScope.launch {

//            recipesLiveDataPrivateTradeRes.value = recipes

            /* wrapEspressoIdlingResource {
                 dataRepositoryRepository.requestTraderesponse().collect {
                     recipesLiveDataPrivateTradeRes.value = it
                 }
             }*/

        }
    }

    fun openRecipeDetails(recipe: DataItem) {
//        openRecipeDetailsPrivate.value = SingleEvent(recipe)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

    fun onSearchClick(recipeName: String) {
        /*recipesLiveDataPrivate.value?.data?.data?.let {
            if (it.isNotEmpty()) {
                for (recipe in it) {
                    if (recipe?.name!!.toLowerCase(ROOT).contains(recipeName.toLowerCase(ROOT))) {
                        recipeSearchFoundPrivate.value = recipe
                        return
                    }
                }
            }
        }*/
        return noSearchFoundPrivate.postValue(Unit)
    }

    fun initData(): TradeResponse {
        try {
            /* val moshi = Moshi.Builder()
                 .add(MyKotlinJsonAdapterFactory())
                 .add(MyStandardJsonAdapters.FACTORY)
                 .build()
             val type: Type = Types.newParameterizedType(List::class.java, TradeItems::class.java)
             val adapter: JsonAdapter<List<TradeItems>> = moshi.adapter(type)
 //            val jsonString = getJson("watchlist2_data.json")

             Log.e("jsonreponse", "::" + jsonString)

             adapter.fromJson(jsonString)?.let {
                 recipes = TradeResponse(ArrayList(it))
                 return recipes
             }*/
//            getJsonDataFromAsset();
        } catch (e: Exception) {
            Log.e("Exception", " :: " + e.message + " :: " + e.localizedMessage)
        }

        return TradeResponse(arrayListOf())
    }

    fun loadDataFromGson(data: TradeItemDataResponse) {

        data?.data?.let {
            recipesLiveDataPrivateTradeRes.value = it
        }

    }

    private fun getJson(path: String): String {
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val inputStream: InputStream = ctx.classLoader.getResourceAsStream(path)
        return inputStream.bufferedReader().use { it.readText() }
    }

    fun getJsonDataFromAsset() {
        val jsonString: String
        try {
            jsonString = context.assets.open("watchlist2_data.json").bufferedReader().use { it.readText() }

            val gson = Gson()
//            val listPersonType = object : TypeToken<TradeResponse>() {}.type
            var tradeItems: TradeItemDataResponse =
                gson.fromJson(jsonString, TradeItemDataResponse::class.java)
            Log.d("size", " :: " + tradeItems.data?.size)

            tradeItems.data?.forEachIndexed { idx, person ->
                Log.i(
                    "data",
                    "> Item $idx:\n$person"
                )
            }

            tradeItems?.data?.let {
                recipesLiveDataPrivateTradeRes.value = emptyList()
                recipesLiveDataPrivateTradeRes.value = it
            }
            //loadDataFromGson(tradeItems)

        } catch (ioException: IOException) {
            ioException.printStackTrace()

        }

    }

    fun getJsonDataFromAssetWithSelectedTab() {
        var jsonString: String = ""
        try {
            if(selectedTab.equals("WATCHLIST 1")){
                jsonString = context.assets.open("watchlist1_data.json").bufferedReader().use { it.readText() }

            }else if(selectedTab.equals("WATCHLIST 2")){
                jsonString = context.assets.open("watchlist2_data.json").bufferedReader().use { it.readText() }

            }else if(selectedTab.equals("WATCHLIST 3")){
                jsonString = context.assets.open("watchlist3_data.json").bufferedReader().use { it.readText() }
            }
//            jsonString = context.assets.open("watchlist2_data.json").bufferedReader().use { it.readText() }

            val gson = Gson()
//            val listPersonType = object : TypeToken<TradeResponse>() {}.type
            var tradeItems: TradeItemDataResponse =
                gson.fromJson(jsonString, TradeItemDataResponse::class.java)
            Log.d("size", " :: " + tradeItems.data?.size)

            val listnewchangedata = ArrayList<DataItem?>();
            tradeItems.data?.forEachIndexed { idx, trademodel ->
                var itemminusplus = Random.nextInt(3);

                Log.d("size", " :: " + tradeItems.data?.get(idx)?.lastTradePrice)
               /* if(itemminusplus.equals(2)){
                    trademodel?.lastTradePrice?.minus(Random.nextInt(10))
                }else{
                    trademodel?.lastTradePrice?.plus(Random.nextInt(10))
                }*/
                trademodel?.lastTradePrice?.plus(Random.nextDouble(10.0))
                trademodel?.dayHigh?.plus(Random.nextDouble(10.0))
                trademodel?.exch?.plus(Random.nextDouble(10.0))
                Log.d("size", " :: " + trademodel?.lastTradePrice+" :: "+trademodel?.lastTradePrice?.plus(Random.nextDouble(10.0)))
                var tempmodel = trademodel;
                Log.d("size", " :: " + tempmodel?.lastTradePrice+" :: "+tempmodel?.dayHigh)
                listnewchangedata.add(tempmodel)
            }



            fixedRateTimer("timer", false, 0L, 3 * 1000) {
                ((context as RecipesListActivity)).runOnUiThread {
                    val listnewchangedata = ArrayList<DataItem?>();
                    tradeItems.data?.forEachIndexed { idx, trademodel ->
                        var itemminusplus = Random.nextInt(3);

                        Log.d("size", " :: " + tradeItems.data?.get(idx)?.lastTradePrice)

                        trademodel?.lastTradePrice?.plus(Random.nextDouble(10.0))
                        trademodel?.dayHigh?.plus(Random.nextDouble(10.0))
                        trademodel?.exch?.plus(Random.nextDouble(10.0))
                        Log.d("size", " :: " + trademodel?.lastTradePrice+" :: "+trademodel?.lastTradePrice?.plus(Random.nextDouble(10.0)))
                        var tempmodel = trademodel;
                        Log.d("size", " :: " + tempmodel?.lastTradePrice+" :: "+tempmodel?.dayHigh)
                        listnewchangedata.add(tempmodel)

                    }
//                    recipesLiveDataPrivateTradeRes.value = listnewchangedata
                }
            }

            listnewchangedata?.let {
                recipesLiveDataPrivateTradeRes.value = emptyList()
                recipesLiveDataPrivateTradeRes.value = it
            }

            //loadDataFromGson(tradeItems)

        } catch (ioException: IOException) {
            ioException.printStackTrace()

        }

    }

    fun getWatchlistname() {
        val jsonString: String
        try {
            jsonString = context.assets.open("watchlist_names.json").bufferedReader().use { it.readText() }

            val gson = Gson()
//            val listPersonType = object : TypeToken<TradeResponse>() {}.type
            var tradeItems: WatchlistResponse =
                gson.fromJson(jsonString, WatchlistResponse::class.java)


            tradeItems.let {
                watchlistname.value =  emptyList()
                watchlistname.value = it.mWName
            }
            //loadDataFromGson(tradeItems)

        } catch (ioException: IOException) {
            ioException.printStackTrace()

        }
    }

}
