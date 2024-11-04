package com.unison.appproductos.Views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import com.unison.appproductos.Model.Product
import com.unison.appproductos.ViewModels.ProductViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormView(navController: NavHostController, product: Product? = null, viewModel: ProductViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (product != null) "" else "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // Contenido de la vista de formulario
        FormContent(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            product = product, // Pasa el producto recuperado a los campos
            onSave = { nombre, precio, descripcion, fecha ->
                if (product == null) {
                    // Crear un nuevo producto
                    val nuevoProducto = Product(
                        nombre = nombre,
                        precio = precio.toDoubleOrNull() ?: 0.0,
                        descripcion = descripcion,
                        fechaRegistro = fecha
                    )
                    viewModel.addProduct(nuevoProducto)
                } else {
                    // Actualizar producto existente
                    val productoActualizado = product.copy(
                        nombre = nombre,
                        precio = precio.toDoubleOrNull() ?: product.precio,
                        descripcion = descripcion,
                        fechaRegistro = fecha
                    )
                    viewModel.updateProduct(productoActualizado)
                }
                navController.popBackStack()
            }
        )
    }
}



@Composable
fun FormContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    product: Product? = null,
    onSave: (String, String, String, String) -> Unit
) {
    val nombre = remember { mutableStateOf(product?.nombre ?: "") }
    val precio = remember { mutableStateOf(product?.precio?.toString() ?: "") }
    val descripcion = remember { mutableStateOf(product?.descripcion ?: "") }
    val fecha = remember { mutableStateOf(product?.fechaRegistro ?: "") }

    Box(
        modifier = Modifier
            .fillMaxSize()
           // .background(Color(0xFFF2E8C6)) // Fondo de la pantalla
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Título dinámico
            if (product == null) {
                Text(
                    text = "Agrega Producto",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            } else {
                Text(
                    text = "Editar Producto",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            }

            // Campos del formulario
            FieldName(
                modifier = Modifier,
                text = nombre.value
            ) { nombre.value = it }
            FieldPrice(
                modifier = Modifier.padding(bottom = 16.dp),
                price = precio.value
            ) { precio.value = it }
            FieldDescription(
                modifier = Modifier.padding(bottom = 16.dp),
                description = descripcion.value
            ) { descripcion.value = it }
            FieldRegisterDate(
                modifier = Modifier.padding(bottom = 16.dp),
                date = fecha.value
            ) { fecha.value = it }

            // Botón de registrar o actualizar
            RegisterButtons(modifier= Modifier.padding(top = 20.dp), navController) {
                onSave(nombre.value, precio.value, descripcion.value, fecha.value)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldName(modifier: Modifier = Modifier, text: String, onValueChange: (String) -> Unit) {
    Text(
        text = "Nombre",
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .padding(top = 30.dp)
    )
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text("Nombre", style = textStyle) },
        shape = RoundedCornerShape(22.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colorScheme.tertiary),
        modifier = modifier.height(70.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldDescription(modifier: Modifier = Modifier, description: String, onValueChange: (String) -> Unit) {
    Text(
        text = "Descripción",
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .padding(top = 10.dp)
    )
    OutlinedTextField(
        value = description,
        onValueChange = onValueChange,
        label = { Text("Descripción", style = textStyle) },
        shape = RoundedCornerShape(22.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colorScheme.tertiary),
        modifier = modifier.height(100.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldRegisterDate(modifier: Modifier = Modifier, date: String, onValueChange: (String) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: date
    Text(
        text = "Fecha",
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .padding(top = 10.dp)
    )

    Box(modifier = Modifier.width(280.dp)) {

        OutlinedTextField(
            value = selectedDate,
            onValueChange = onValueChange,
            label = { Text("Fecha", style = textStyle) },
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colorScheme.tertiary),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date",
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .width(270.dp),
            shape = RoundedCornerShape(22.dp),
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldPrice(modifier: Modifier = Modifier, price: String, onValueChange: (String) -> Unit) {
    Text(
        text = "Precio",
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .padding(top = 10.dp),
    )
    OutlinedTextField(
        value = price,
        onValueChange = onValueChange,
        label = { Text("Precio", style = textStyle) },
        shape = RoundedCornerShape(22.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colorScheme.tertiary),
        singleLine = true,
        leadingIcon = {
            Text(
                text = "$",
                fontSize = 25.sp,
                modifier = Modifier.padding(start = 8.dp),
                color = Color(0xFF000000)
            )
        },
        modifier = modifier
            .height(70.dp)
            .width(280.dp)
    )
}
@Composable
fun RegisterButtons(modifier: Modifier = Modifier, navController: NavHostController, onRegisterClick: () -> Unit) {
    val context = LocalContext.current // Obtener el contexto actual

    Row(
        modifier = modifier.padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElevatedButton(
            onClick = {
                // Muestra un Toast cuando se hace clic en el botón "Registrar"
                Toast.makeText(context, "Producto agregado", Toast.LENGTH_SHORT).show()
                onRegisterClick() // Llama la función de registro
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer), // Color personalizado
            modifier = Modifier
                .padding(10.dp)
                .height(60.dp) // Ajusta la altura del botón
                .width(150.dp) // Ajusta el ancho del botón
        ) {
            Text(
                text = "Registrar",
                color = Color(0xFFffffff),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        ElevatedButton(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary), // Color personalizadosecondaryContainer),
            modifier = Modifier
                .padding(10.dp)
                .height(60.dp) // Ajusta la altura del botón
                .width(150.dp)
        ) {
            Text(
                text = "Cancelar",
                color = Color(0xFFffffff),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}



val textStyle = androidx.compose.ui.text.TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.ExtraBold,
    color = Color(0xFFFFFFF)
)

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}


