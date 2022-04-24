package com.bestsales.repositories.mappers

import com.bestsales.network.api.search.SearchResponse
import com.bestsales.repositories.domain.Price
import com.bestsales.repositories.domain.ProductDto


fun SearchResponse.mapToDomain(): List<ProductDto> {
    return products.map { item ->
        return@map ProductDto(
            id = item.id,
            name = item.name,
            imgLink = item.imageLink,
            price = Price(
                salesPrice = item.salesPriceIncVat,
                basePrice = item.listPriceIncVat ?: 0.0
            ),
            isNextDayDelivery = item.nextDayDelivery
        )
    }
}