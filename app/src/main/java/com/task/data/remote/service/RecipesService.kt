package com.task.data.remote.service

import com.task.data.dto.recipes.RecipesItem
import com.task.data.dto.trade.TradeResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Sumeetbhut
 */

interface RecipesService {
    @GET("recipes.json")
    suspend fun fetchRecipes(): Response<List<TradeResponse>>
}
