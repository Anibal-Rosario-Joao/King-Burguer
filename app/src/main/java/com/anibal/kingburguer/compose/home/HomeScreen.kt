package com.anibal.kingburguer.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

data class Product(
    val name: String
)

data class Category(
    val name: String,
    val products: List <Product>
)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
    val categories = listOf(
        Category(
            "Sobremesa",
            listOf(
                Product("Sobremesa 1"),
                Product("Sobremesa 2"),
                Product("Sobremesa 3"),
                Product("Sobremesa 4")
            )
        ),
        Category(
            "Vegetariano",
            listOf(
                Product("Vegetariano 1"),
                Product("Vegetariano 2"),
                Product("Vegetariano 3"),
                Product("Vegetariano 4")
            )
        ),
        Category(
            "Bovino",
            listOf(
                Product("Bovino 1"),
                Product("Bovino 2"),
                Product("Bovino 3"),
                Product("Bovino 4"),
                Product("Bovinho 5")
            )
        ),
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(Color.Red),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(categories){ cat ->
            Text(
                text = cat.name,
                style = MaterialTheme.typography.headlineLarge
            )
            LazyRow() {
                items(cat.products){ product ->
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                        text = product.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenLigthPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        HomeScreen(modifier = Modifier.fillMaxSize())
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenDarkPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = true){
        HomeScreen(modifier = Modifier.fillMaxSize())
    }
}