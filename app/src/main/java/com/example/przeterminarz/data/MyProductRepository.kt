package com.example.przeterminarz.data

import com.example.przeterminarz.model.Product
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object MyProductRepository : ProductRepository {
    private var productList = mutableListOf(
        Product("Mleko", parseDate("10-05-2024"), "Produkty spożywcze", 2),
        Product("Chleb", parseDate("12-05-2024"), "Produkty spożywcze", 1),
        Product("Jabłka", parseDate("15-05-2024"), "Produkty spożywcze", 5),
        Product("Płatki śniadaniowe", parseDate("20-05-2024"), "Produkty spożywcze", 1),
        Product("Aspiryna", parseDate("01-06-2024"), "Leki"),
        Product("Krem", parseDate("10-07-2023"), "Kosmetyki"),
        Product("Szampon", parseDate("15-04-2024"), "Kosmetyki", 1),
        Product("Pasta do zębów", parseDate("23-05-2024"), "Kosmetyki", 1),
    )

    private fun parseDate(date: String): LocalDate {
        val dateTimeFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
        return LocalDate.parse(date, dateTimeFormatter)
    }

    override fun getProductList(): List<Product> = productList

    override fun add(product: Product) {
        productList.add(product)
    }

    override fun getProductById(id: Int): Product = productList[id]
    override fun set(id: Int, product: Product) {
        productList[id] = product
    }

    override fun removeById(id: Int) {
        productList.removeAt(id)
    }

    override fun getProductListByCategory(selectedCategory: String): List<Product> {
        return productList.filter { it.category == selectedCategory }
    }
}