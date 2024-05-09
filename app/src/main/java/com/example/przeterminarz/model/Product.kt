package com.example.przeterminarz.model

import java.time.LocalDate

data class Product(
    val name: String,
    val expirationDate: LocalDate,
    val category: String,
    val quantity: Int? = null
) {
    fun isExpired(): Boolean {
        val currentDate = LocalDate.now()
        return expirationDate.isBefore(currentDate)
    }
}
