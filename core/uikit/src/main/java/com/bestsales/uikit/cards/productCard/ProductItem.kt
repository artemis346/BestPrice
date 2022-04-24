package com.bestsales.uikit.cards.productCard

data class ProductItem(
    val title: String,
    val imgLink: String,
    val price: String,
    val basePrice: String,
    val isNextDayDelivery: Boolean
)