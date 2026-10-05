package com.anibal.kingburguer.compose.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.anibal.kingburguer.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.anibal.kingburguer.common.currency
import com.anibal.kingburguer.component.KingAlert
import com.anibal.kingburguer.component.KingButton
import com.anibal.kingburguer.data.CategoryDetailResponse
import com.anibal.kingburguer.data.ProductDetailResponse
import com.anibal.kingburguer.ui.theme.KingBurguerTheme
import com.anibal.kingburguer.viewmodels.ProductViewModel
import java.util.Date

@Composable
fun ProductScreen(
    modifier: Modifier,
   viewModel: ProductViewModel = viewModel(factory = ProductViewModel.factory),
    onBackClicked: () -> Unit
){
    val state = viewModel.uiState.collectAsState().value
    ProductScreen(
        modifier, state,
        couponClicked = { viewModel.createCoupon() },
        onCouponGenerated = {
            viewModel.reset()
        onBackClicked()
        }
    )
}

@Composable
fun ProductScreen(
    modifier: Modifier,
    state: ProductUiState,
    couponClicked: () -> Unit,
    onCouponGenerated: () -> Unit,

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

                state.productDetail?.let{
                    ProductScreen(modifier, state.productDetail, couponClicked )
                }
                state.coupon?.let{
                    KingAlert(
                        onDismissRequest = { /*TODO*/ },
                        confirmationButton = onCouponGenerated,
                        dialogTitle = stringResource(R.string.app_name),
                        dialogText = stringResource(R.string.coupon_generated, state.coupon.coupon),
                        Icons.Filled.Info

                    )
                }

            }
        }
    }
}

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    product: ProductDetailResponse,
    couponClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    model = product.pictureUrl ,
                    placeholder = painterResource(R.drawable.logo),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = product.name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge

                    )

                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp),
                        text = product.price.currency(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, bottom = 56.dp),
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            KingButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                text = stringResource(R.string.get_coupon),
                onClick =  couponClicked
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenLigthPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        ProductScreen(
            product = ProductDetailResponse (name = "Produto A", id = 1, price = 21.99, pictureUrl = "", createdDate = Date() , description = "Tenho grande interesse em fazer parte da Save the Children International, uma instituição reconhecida pelo seu impacto positivo na vida das comunidades, especialmente no apoio às crianças e famílias em situação de vulnerabilidade. Acredito que trabalhar nesta organização representa não apenas uma oportunidade profissional, mas também uma forma de contribuir para uma causa humanitária.",
        categoryResponse = CategoryDetailResponse(
            id = 1,
            name = ""
        )
        )
        ){}

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenDarkPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = true){
        ProductScreen(
            product = ProductDetailResponse (name = "Produto A", id = 1, price = 21.99, pictureUrl = "", createdDate = Date() , description = "Tenho grande interesse em fazer parte da Save the Children International, uma instituição reconhecida pelo seu impacto positivo na vida das comunidades, especialmente no apoio às crianças e famílias em situação de vulnerabilidade. Acredito que trabalhar nesta organização representa não apenas uma oportunidade profissional, mas também uma forma de contribuir para uma causa humanitária.",
                categoryResponse = CategoryDetailResponse(
                    id = 1,
                    name = ""
                )
            )
        ){}

    }
}