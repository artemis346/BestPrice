package com.bestsales.repositories.domain

data class ProductDto(
    val id: Long,
    val name: String,
    val imgLink: String,
    val price: Price,
    val isNextDayDelivery: Boolean
)

data class Price(
    val basePrice: Double,
    val salesPrice: Double
)