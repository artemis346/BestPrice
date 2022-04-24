package com.bestsales.network.api.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(

    @SerializedName("products")
    val products: List<Product>,

    @SerializedName("currentPage")
    val currentPage: Int,

    @SerializedName("totalResults")
    val totalResults: Int,

    @SerializedName("pageCount")
    val pageCount: Int,
)

data class Product(
    @SerializedName("productId")
    val id: Long,

    @SerializedName("productName")
    val name: String,

    @SerializedName("productImage")
    val imageLink: String,

    @SerializedName("nextDayDelivery")
    val nextDayDelivery: Boolean,

    @SerializedName("salesPriceIncVat")
    val salesPriceIncVat: Double,

    @SerializedName("listPriceIncVat")
    val listPriceIncVat: Double? = null,

    @SerializedName("listPriceExVat")
    val listPriceExVat: Double? = null,
)
