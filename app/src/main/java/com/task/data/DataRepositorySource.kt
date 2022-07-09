package com.task.data

import com.task.data.dto.recipes.Recipes
import com.task.data.dto.login.LoginRequest
import com.task.data.dto.login.LoginResponse
import com.task.data.dto.trade.TradeResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Sumeetbhut
 */

interface DataRepositorySource {
    suspend fun requestRecipes(): Flow<Resource<TradeResponse>>
    suspend fun requestTraderesponse(): Flow<Resource<TradeResponse>>
    suspend fun doLogin(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
    suspend fun addToFavourite(id: String): Flow<Resource<Boolean>>
    suspend fun removeFromFavourite(id: String): Flow<Resource<Boolean>>
    suspend fun isFavourite(id: String): Flow<Resource<Boolean>>
}
