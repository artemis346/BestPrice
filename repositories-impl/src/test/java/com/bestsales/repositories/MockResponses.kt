package com.bestsales.repositories

import com.bestsales.network.api.search.Product
import com.bestsales.network.api.search.SearchResponse

val responseValid = SearchResponse(
    products = listOf(
        Product(
            id = 121231,
            name = "test",
            imageLink = "https://image.coolblue.nl/300x750/products/818870",
            nextDayDelivery = true,
            salesPriceIncVat = 600.00
        ),
        Product(
            id = 121232,
            name = "test",
            imageLink = "https://image.coolblue.nl/300x750/products/818870",
            nextDayDelivery = true,
            salesPriceIncVat = 654.00,
            listPriceIncVat = 655.99,
            listPriceExVat = 517.99,
        )
    ),
    currentPage = 1,
    totalResults = 2,
    pageCount = 2
)

val responseEmpty = SearchResponse(
    products = listOf(),
    currentPage = 1,
    totalResults = 1,
    pageCount = 1
)

val responseNull = SearchResponse(
    products = null,
    currentPage = 1,
    totalResults = 1,
    pageCount = 1
)