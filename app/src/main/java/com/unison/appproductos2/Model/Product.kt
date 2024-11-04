package com.unison.appproductos.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "productos",)
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val nombre: String = "",

    @ColumnInfo(name = "description")
    val descripcion: String = "",

    @ColumnInfo(name = "price")
    val precio: Double = 0.0,

    @ColumnInfo(name = "date")
    val fechaRegistro: String = ""
)
