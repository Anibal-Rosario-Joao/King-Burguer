package com.anibal.kingburguer.compose.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anibal.kingburguer.R
import com.anibal.kingburguer.common.currency
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

data class Product(
    val name: String,
    @DrawableRes val picture: Int = R.drawable.example,
    val price: Double = 20.0
)

data class Category(
    val name: String,
    val products: List <Product>
)
@OptIn(ExperimentalFoundationApi::class)
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(Color.Blue),
                painter = painterResource(R.drawable.highlight),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            OutlinedButton(
                modifier = Modifier
                    .padding(bottom = 12.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 6.dp
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.get_coupon),
                    color = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsIndexed(categories) { index, cat ->
                val topPadding = if (index == 0) 20.dp else 0.dp
                val bottomPadding = if (index == categories.size - 1) 20.dp else 0.dp
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 12.dp, top = topPadding),
                    text = cat.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineMedium
                )
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = bottomPadding)
                    ) {
                        itemsIndexed(cat.products) { index, product ->
                            val startPadding = if (index == 0) 20.dp else 8.dp
                            val endPadding = if (index == categories.size - 1) 20.dp else 8.dp

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier
                                    .widthIn(max = 160.dp)
                                    .padding(start = startPadding, end = endPadding)
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(140.dp, 180.dp)
                                        .border(
                                            BorderStroke(0.3.dp, Color.Gray),
                                            RoundedCornerShape(8.dp)
                                        ),
                                    painter = painterResource(product.picture),
                                    contentDescription = product.name
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    color = MaterialTheme.colorScheme.inverseSurface,
                                    text = product.name,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Text(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(4.dp)
                                        ),
                                    color = MaterialTheme.colorScheme.surface,
                                    text = product.price.currency(),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
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