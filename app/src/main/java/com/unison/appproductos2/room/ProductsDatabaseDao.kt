package com.unison.appproductos2.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unison.appproductos.Model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDatabaseDao{
    @Query("SELECT * FROM productos")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM productos WHERE id =:id LIMIT 1")
    fun getProductById(id: Int): Flow<Product>

    @Query("SELECT * FROM productos WHERE name =:nombre")
    fun getProductByName(nombre: String): Flow<Product>


    @Insert(entity = Product::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: Product)

    @Update(entity = Product::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)


}