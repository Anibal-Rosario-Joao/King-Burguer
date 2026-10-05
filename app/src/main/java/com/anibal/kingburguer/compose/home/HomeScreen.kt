package com.anibal.kingburguer.compose.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.anibal.kingburguer.R
import com.anibal.kingburguer.common.currency
import com.anibal.kingburguer.data.CategoryResponse
import com.anibal.kingburguer.data.HighlightProductResponse
import com.anibal.kingburguer.ui.theme.KingBurguerTheme
import com.anibal.kingburguer.viewmodels.HomeViewModel
import java.util.Date


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory),
    onProductClicked: (Int) -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    HomeScreen(modifier, state, onProductClicked)
}

@Composable
fun HomeScreen(
    modifier: Modifier,
    state: HomeUiState,
    onProductClicked: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        HighlightView( state = state.highlightUiState,onProductClicked)
        CategoriesView( state = state.categoryUiState,onProductClicked)
    }
}

@Composable
private fun HighlightView(
    state: HighlightUiState,
    onProductClicked: (Int) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ){
        when{
            state.isLoading ->{
                CircularProgressIndicator()
            }
            state.error != null ->{
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            state.product != null ->{
                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp),
                        model = state.product.pictureUrl,
                        placeholder = painterResource(R.drawable.logo),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )

                    Button(
                        modifier = Modifier
                            .padding(bottom = 12.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 6.dp
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            onProductClicked(state.product.productId)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.show_more),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoriesView(
    state: CategoryUiState,
    onProductClicked: (Int) -> Unit
    ){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        when{
            state.isLoading ->{
                CircularProgressIndicator()
            }
            state.error != null ->{
                Text(
                    //text = state.error
                    text = stringResource(R.string.erro_message),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
            else ->{
                HomeScreen(modifier = Modifier, categories =state.categories, onProductClickeds = onProductClicked)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
   categories: List<CategoryResponse>,
    onProductClickeds: (Int) -> Unit
){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
          //  contentPadding = contentPadding,
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
                            val endPadding = if (index == cat.products.size - 1) 20.dp else 8.dp

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier
                                    .widthIn(max = 160.dp)
                                    .padding(start = startPadding, end = endPadding)
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(140.dp, 180.dp)
                                        .border(
                                            BorderStroke(0.3.dp, Color.Gray),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable{onProductClickeds(product.id)},

                                    model = product.pictureUrl,
                                    placeholder =  painterResource(R.drawable.logo),
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenLoadingPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        val state = HomeUiState(
            categoryUiState = CategoryUiState(isLoading = true)
        )
        HomeScreen(modifier = Modifier.fillMaxSize(), state){}
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        val state = HomeUiState(
            categoryUiState = CategoryUiState(error = "Erro de teste !!!!!")
        )
        HomeScreen(modifier = Modifier.fillMaxSize(), state){}
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenEmptyPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        val state = HomeUiState(
            categoryUiState = CategoryUiState(categories = emptyList())
        )
        HomeScreen(modifier = Modifier.fillMaxSize(), state){}
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightHightlightPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        val state = HomeUiState(
            highlightUiState = HighlightUiState(
                product = HighlightProductResponse(
                    id = 0,
                    productId = 0,
                    pictureUrl = "https://pracehold.co/600x400",
                    createdDate = Date()
                )
            )
        )
        HomeScreen(modifier = Modifier.fillMaxSize(), state){}
    }
}