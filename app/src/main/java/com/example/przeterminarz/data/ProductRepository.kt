package com.example.przeterminarz.data

import com.example.przeterminarz.model.Product

interface ProductRepository {
    fun getProductList(): List<Product>
    fun add(product: Product)
    fun getProductById(id: Int): Product
    fun set(id: Int, product: Product)
    fun removeById(id: Int)
    fun getProductListByCategory(selectedCategory: String): List<Product>
}