package com.anibal.kingburguer.compose.coupon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

// Modelo de dados para o Cupão
data class Coupon(
    val id: Int,
    val productId: Int,
    val code: String,
    val expirationAt: String,
    val createdAt: String
)

@Composable
fun CouponScreen(
    modifier: Modifier = Modifier,
    coupons: List<Coupon> = mockCoupons() // Por defeito usa dados de teste (depois virão do ViewModel)
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                text = "Meus Cupões",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 12.dp)
            )

            if (coupons.isEmpty()) {
                EmptyCouponState()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(coupons) { coupon ->
                        CouponCard(coupon = coupon)
                    }
                }
            }
        }
    }
}

@Composable
fun CouponCard(modifier: Modifier = Modifier, coupon: Coupon) {
    val clipboardManager = LocalClipboardManager.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Ícone à esquerda
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocalOffer,
                        contentDescription = "Cupão",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Informações do Cupão
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Desconto Especial",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Válido para o produto #${coupon.productId}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Linha divisória pontilhada (estilizada com divider simples)
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Rodapé do Card: Código e Botão Copiar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Válido até: ${formatDate(coupon.expirationAt)}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error // Vermelho para destacar a expiração
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Box do Código
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background,
                                RoundedCornerShape(8.dp)
                            )
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {

                        Text(
                            text = coupon.code,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 2.sp
                        )
                    }
                }

                // Botão de Copiar
                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(coupon.code))
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                        .size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ContentCopy,
                        contentDescription = "Copiar Código",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyCouponState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.LocalOffer,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Nenhum cupão disponível no momento",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Função auxiliar para formatar a data que vem da API
fun formatDate(dateString: String): String {
    return try {
        // Separa a data do tempo ("2025-03-14" e "12:24:19")
        val parts = dateString.split("T")
        val dateParts = parts[0].split("-")
        // Retorna no formato PT: DD/MM/AAAA
        "${dateParts[2]}/${dateParts[1]}/${dateParts[0]}"
    } catch (e: Exception) {
        dateString // Em caso de erro, retorna a string original
    }
}

// Função auxiliar de mock (dados fictícios) para os Previews
fun mockCoupons(): List<Coupon> {
    return listOf(
        Coupon(
            id = 510,
            productId = 1,
            code = "FHMQGA",
            expirationAt = "2025-03-14T12:24:19",
            createdAt = "2025-02-27T12:24:19"
        ),
        Coupon(
            id = 511,
            productId = 3,
            code = "BURGER10",
            expirationAt = "2025-04-10T23:59:00",
            createdAt = "2025-02-28T09:10:00"
        ),
        Coupon(
            id = 512,
            productId = 5,
            code = "VEGGIEFREE",
            expirationAt = "2025-03-20T14:30:00",
            createdAt = "2025-03-01T10:00:00"
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CouponScreenLightPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        CouponScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CouponScreenDarkPreview() { // Corrigi o nome da função que estava ProfileScreenDarkPreview
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        CouponScreen()
    }
}