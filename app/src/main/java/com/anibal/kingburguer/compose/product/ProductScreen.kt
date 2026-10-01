package com.anibal.kingburguer.compose.product

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anibal.kingburguer.common.currency
import com.anibal.kingburguer.component.KingButton
import com.anibal.kingburguer.compose.home.Product
import com.anibal.kingburguer.ui.theme.KingBurguerTheme
import com.anibal.kingburguer.viewmodels.ProductViewModel

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
   viewModel: ProductViewModel = viewModel(factory = ProductViewModel.factory)
){
    ProductScreen(modifier,viewModel.product)
}

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    product: Product
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
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    painter = painterResource(product.picture),
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
                    text = "Tenho grande interesse em fazer parte da Save the Children International, uma instituição reconhecida pelo seu impacto positivo na vida das comunidades, especialmente no apoio às crianças e famílias em situação de vulnerabilidade. Acredito que trabalhar nesta organização representa não apenas uma oportunidade profissional, mas também uma forma de contribuir para uma causa humanitária.\nPossuo competências básicas em organização de materiais, controlo de stock e apoio logístico, bem como facilidade de trabalho em equipa, responsabilidade e dedicação no cumprimento das tarefas atribuídas. Tenho ainda disponibilidade para aprender e adaptar-me às exigências do ambiente de trabalho, mantendo sempre o compromisso com a eficiência e a qualidade do serviço",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            KingButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                text = stringResource(R.string.get_coupon)
            ) { }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenLigthPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        ProductScreen(product = Product (1,"Tenho grande interesse em fazer parte da Save the Children International, uma instituição reconhecida pelo seu impacto positivo na vida das comunidades, especialmente no apoio às crianças e famílias em situação de vulnerabilidade. Acredito que trabalhar nesta organização representa não apenas uma oportunidade profissional, mas também uma forma de contribuir para uma causa humanitária."))
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenDarkPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = true){
        ProductScreen(product = Product (1,"Teste"))
    }
}