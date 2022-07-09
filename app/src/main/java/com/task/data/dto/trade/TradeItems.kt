package com.task.data.dto.trade

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class TradeItems(
	@Json(name = "DayLow")
	val dayLow: Double? = null,

	@Json(name = "Quarter")
	val quarter: Int? = null,

	val dayHigh: Int? = null,
	val high52Week: Int? = null,
	val ePS: Int? = null,
	val shortName: String? = null,
	val lastTradePrice: Double? = null,
	val pClose: Double? = null,
	val name: String? = null,
	val lastTradeTime: String? = null,
	val month: Int? = null,
	val year: Int? = null,
	val volume: Int? = null,
	val low52Week: Int? = null,
	val fullName: String? = null,
	val scripCode: Int? = null,
	val exch: String? = null,
	val exchType: String? = null,
	val nseBseCode: Int? = null
): Parcelable