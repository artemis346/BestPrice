package com.bestsales.repositories.mappers

import com.bestsales.repositories.domain.Price
import com.bestsales.repositories.domain.ProductDto
import com.bestsales.repositories.responseEmpty
import com.bestsales.repositories.responseNull
import com.bestsales.repositories.responseValid
import org.junit.Test

internal class SearchResponseMapperTest {

    @Test
    fun `invoke mapper when convert api to domain POSITIVE CASE`() {
        val response = responseValid.mapToDomain()
        val result = listOf(
            ProductDto(
                id = 121231,
                name = "test",
                imgLink = "https://image.coolblue.nl/300x750/products/818870",
                price = Price(
                    basePrice = 0.00, salesPrice = 600.00
                ),
                isNextDayDelivery = true
            ),
            ProductDto(
                id = 121232,
                name = "test",
                imgLink = "https://image.coolblue.nl/300x750/products/818870",
                price = Price(
                    basePrice = 655.99, salesPrice = 654.00
                ),
                isNextDayDelivery = true
            )
        )
        assert(response.deepEquals(result))
    }

    @Test
    fun `invoke mapper when convert api to domain EMPTY CASE`() {
        val response = responseEmpty.mapToDomain()
        val result = listOf<ProductDto>()
        assert(response.deepEquals(result))
    }

    @Test
    fun `invoke mapper when convert api to domain NULL CASE`() {
        val response = responseNull.mapToDomain()
        val result = listOf<ProductDto>()
        assert(response.deepEquals(result))
    }

    private fun List<ProductDto>.deepEquals(other: List<ProductDto>): Boolean {
        return this.size == other.size && this.mapIndexed { index, productDto ->
            productDto == other[index]
        }.all { it }
    }
}