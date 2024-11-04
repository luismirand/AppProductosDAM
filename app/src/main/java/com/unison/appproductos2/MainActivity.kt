package com.unison.appproductos2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.unison.appproductos.Navigation.NavManager
import com.unison.appproductos.ui.theme.AppProductosTheme
import com.unison.appproductos.ViewModels.ProductViewModel
import com.unison.appproductos.Views.*
import com.unison.appproductos2.room.ProductDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppProductosTheme {


                val database = Room.databaseBuilder(
                    applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                ).build()


                val dao = database.productsDao()
                val productViewModel = ProductViewModel(dao)


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { paddingValues ->

                    NavManager(
                        productViewModel = productViewModel,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}
