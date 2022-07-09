package com.task.data.dto.trade

import com.google.gson.annotations.SerializedName

data class TradeItemDataResponse(

	@field:SerializedName("Status")
	val status: Double? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("MarketWatchName")
	val marketWatchName: String? = null,

	@field:SerializedName("Data")
	val data: List<DataItem?>? = null
)

data class DataItem(

	@field:SerializedName("DayLow")
	val dayLow: Double? = null,

	@field:SerializedName("Quarter")
	val quarter: Double? = null,

	@field:SerializedName("DayHigh")
	val dayHigh: Double? = null,

	@field:SerializedName("High52Week")
	val high52Week: Double? = null,

	@field:SerializedName("EPS")
	val ePS: Double? = null,

	@field:SerializedName("ShortName")
	val shortName: String? = null,

	@field:SerializedName("LastTradePrice")
	val lastTradePrice: Double? = null,

	@field:SerializedName("PClose")
	val pClose: Double? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("LastTradeTime")
	val lastTradeTime: String? = null,

	@field:SerializedName("Month")
	val month: Double? = null,

	@field:SerializedName("Year")
	val year: Double? = null,

	@field:SerializedName("Volume")
	val volume: Double? = null,

	@field:SerializedName("Low52Week")
	val low52Week: Double? = null,

	@field:SerializedName("FullName")
	val fullName: String? = null,

	@field:SerializedName("ScripCode")
	val scripCode: Double? = null,

	@field:SerializedName("Exch")
	val exch: String? = null,

	@field:SerializedName("ExchType")
	val exchType: String? = null,

	@field:SerializedName("NseBseCode")
	val nseBseCode: Double? = null
)
