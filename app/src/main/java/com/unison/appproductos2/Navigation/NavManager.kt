package com.unison.appproductos.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.unison.appproductos.Model.Product
import com.unison.appproductos.Views.HomeView
import com.unison.appproductos.Views.ListView
import com.unison.appproductos.Views.PresentationView
import com.unison.appproductos.Views.FormView
import com.unison.appproductos.ViewModels.ProductViewModel
import com.unison.appproductos2.Views.EditView

@Composable
fun NavManager(
    navController: NavHostController = rememberNavController(),
    productViewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavDestinations.HomeView,
        modifier = modifier
    ) {
        composable(NavDestinations.HomeView) {
            HomeView(navController = navController)
        }
        composable(NavDestinations.PresentationView) {
            PresentationView(navController = navController)
        }
        composable(NavDestinations.ListView) {
            ListView(viewModel = productViewModel, navController = navController)
        }
        composable(NavDestinations.FormView) {
            FormView(navController = navController, viewModel = productViewModel)
        }
        // Ruta de edición con el parámetro productId
        composable(
            route = "edit_view/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            val product = productViewModel.getProductById(productId)
            EditView(navController = navController, product = product, viewModel = productViewModel)
        }
    }
}





