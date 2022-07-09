package com.task.data.dto.trade

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Query

@JsonClass(generateAdapter = false)
@Parcelize
data class TradeResponse(
	@Json(name = "Data")
	val data: List<TradeItems?>? = null
): Parcelable



