package com.task.data.dto.trade

import com.google.gson.annotations.SerializedName

data class WatchlistResponse(

	@field:SerializedName("Status")
	val status: Int? = null,

	@field:SerializedName("MWName")
	val mWName: List<Watchlistname?>? = null
)

data class Watchlistname(

	@field:SerializedName("MwatchName")
	val mwatchName: String? = null
)
