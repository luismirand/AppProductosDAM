package com.unison.appproductos2.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unison.appproductos.Model.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDatabaseDao
}