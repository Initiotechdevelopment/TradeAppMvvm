package com.task.data.remote

import com.task.data.Resource
import com.task.data.dto.recipes.Recipes
import com.task.data.dto.trade.TradeResponse

/**
 * Created by Sumeetbhut
 */

internal interface RemoteDataSource {
    suspend fun requestRecipes(): Resource<TradeResponse>
}
