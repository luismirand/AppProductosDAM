package com.unison.appproductos.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unison.appproductos.Model.Product
import com.unison.appproductos.State.ProductState
import com.unison.appproductos2.room.ProductsDatabaseDao
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductViewModel(private val dao: ProductsDatabaseDao): ViewModel() {


    var estado by mutableStateOf(ProductState())
        private set


    init {
        viewModelScope.launch {
            // Simular carga de datos
            dao.getProducts().collectLatest{
                estado = estado.copy(productsList = it)
            }
            delay(2000)

        }
    }


    fun addProduct(product: Product){

        if(product.nombre.isBlank() || product.descripcion.isBlank() || product.precio == 0.0 )
            return;

        CoroutineScope(Dispatchers.IO).launch {            dao.addProduct(product)

        }
    }

    fun updateProduct(product: Product){
        CoroutineScope(Dispatchers.IO).launch {
            dao.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product){
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteProduct(product)
        }
    }


    fun getProductById(id: Int?): Product? {
        return runBlocking {
            dao.getProductById(id ?: 0).firstOrNull()
        }
    }


    fun getProductFlowById(id: Int?): Flow<Product> {
        return dao.getProductById(id ?: 0)
    }


}

