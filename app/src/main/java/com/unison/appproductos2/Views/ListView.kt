package com.unison.appproductos.Views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.unison.appproductos.Model.Product
import com.unison.appproductos.Navigation.NavDestinations
import com.unison.appproductos.ViewModels.ProductViewModel
import androidx.compose.material3.*
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListView(viewModel: ProductViewModel, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavDestinations.FormView)
                },
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Producto",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()

                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Titulo
                Text(
                    text = "Productos",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 30.dp)
                )

                // Estado del viewModel
                val estado = viewModel.estado

                var productToDelete: Product by remember { mutableStateOf(Product()) }
                var openDeleteDialog by remember { mutableStateOf(false) }
                val context = LocalContext.current

                // Diálogo de confirmación para eliminar
                if (openDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { openDeleteDialog = false },
                        title = { Text("Eliminar Producto?") },
                        text = { Text("Esta acción no puede ser deshecha.") },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.deleteProduct(productToDelete)
                                Toast.makeText(
                                    context,
                                    "Producto eliminado",
                                    Toast.LENGTH_SHORT
                                ).show()
                                openDeleteDialog = false // Cierra el diálogo
                            }) {
                                Text("Eliminar", color = MaterialTheme.colorScheme.error)


                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { openDeleteDialog = false }) {
                                Text("Cancelar", color = MaterialTheme.colorScheme.tertiary)
                            }
                        }
                    )
                }

                // Carga de productos
                if (estado.estaCargando) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    if (estado.productsList.isEmpty()) {
                        // Mensaje si no hay productos
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay productos disponibles.",
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(bottom = 25.dp)
                        ) {
                            items(estado.productsList) { product ->
                                ProductItemCard(
                                    product = product,
                                    onEditClick = {

                                        product.id?.let {
                                            navController.navigate("edit_view/$it")
                                        }
                                    },
                                    onDeleteClick = {
                                        productToDelete = product
                                        openDeleteDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}






@Composable
fun ProductItemCard(
    product: Product,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Nombre del producto
                Text(
                    text = product.nombre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                // Precio
                Text(
                    text = "$${product.precio} pesos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                // Descripción
                Text(
                    text = product.descripcion,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            // Fila de botones para editar y eliminar
            Row(
                modifier = Modifier.padding(start = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de editar
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
                // Espaciado entre botones
                Spacer(modifier = Modifier.width(8.dp))
                // Botón de eliminar
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}



