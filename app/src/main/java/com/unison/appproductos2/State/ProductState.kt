package com.unison.appproductos.State

import com.unison.appproductos.Model.Product

data class ProductState(
//    val productos: List<Product> = listOf(),
    val estaCargando: Boolean = false,
    val productsList: List<Product> = emptyList()
)
