package com.unison.appproductos.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.unison.appproductos.Navigation.NavDestinations
import com.unison.appproductos2.R

@Composable
fun HomeView(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()

            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        ElevatedCard(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer) // Color de la tarjeta
        ) {

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.empresa), // imagen
                    contentDescription = "Logo de la Empresa",
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(0.6f), //  tamaño de la imagen
                    contentScale = ContentScale.Fit
                )


                Text(
                    text = "CuboCuadrado Inc.",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(top = 20.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )


                Button(
                    onClick = { navController.navigate(NavDestinations.ListView) },
                    modifier = Modifier.padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer, // Fondo blanco
                        contentColor = Color.Black    // Letras negras
                    )
                ) {
                    Text("Ir a Lista de productos")
                }

                Button(
                    onClick = { navController.navigate(NavDestinations.PresentationView) },
                    modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer, // Fondo blanco
                        contentColor = Color.Black    // Letras negras
                    )
                ) {
                    Text("Ir a Presentacion")
                }
            }
        }


        Text(
            text = "Luis Alberto Miranda Díaz",
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}
