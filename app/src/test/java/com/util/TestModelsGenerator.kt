package com.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.task.data.dto.trade.TradeItems
import com.task.data.dto.trade.TradeResponse
import com.task.data.remote.moshiFactories.MyKotlinJsonAdapterFactory
import com.task.data.remote.moshiFactories.MyStandardJsonAdapters
import java.io.File
import java.lang.reflect.Type


/**
 * Created by Sumeetbhut
 */

class TestModelsGenerator {
//    private var recipes: Recipes = Recipes(arrayListOf())
    private var recipes: TradeResponse = TradeResponse(arrayListOf())

    init {
        val moshi = Moshi.Builder()
                .add(MyKotlinJsonAdapterFactory())
                .add(MyStandardJsonAdapters.FACTORY)
                .build()
        val type: Type = Types.newParameterizedType(List::class.java, TradeItems::class.java)
        val adapter: JsonAdapter<List<TradeItems>> = moshi.adapter(type)
//        val jsonString = getJson("RecipesApiResponse.json")
        val jsonString = getJson("watchlist2_data.json")
        adapter.fromJson(jsonString)?.let {
            recipes = TradeResponse(ArrayList(it))
        }
        print("this is $recipes")
    }

    fun generateRecipes(): TradeResponse {
        return recipes
    }

    fun generateRecipesModelWithEmptyList(): TradeResponse {
        return TradeResponse(arrayListOf())
    }

    fun generateRecipesItemModel(): TradeItems? {
        return recipes.data?.get(0)
    }

    fun getStubSearchTitle(): String? {
        return recipes.data?.get(0)?.name
    }


    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}
