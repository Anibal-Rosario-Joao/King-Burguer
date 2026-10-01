package com.anibal.kingburguer.compose.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anibal.kingburguer.R
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Header: Foto e Nome
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Usuario A",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Membro King Burguer",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Cartão com os detalhes do utilizador
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    ProfileProperty(R.string.prop_id, "2", Icons.Filled.Numbers)
                    ProfileProperty(R.string.prop_name, "Usuario A", Icons.Filled.Person)
                    ProfileProperty(R.string.prop_email, "usuarioA@gmail.com", Icons.Filled.Email)
                    ProfileProperty(R.string.prop_birthday, "24/05/2004", Icons.Filled.Cake, showDivider = false)
                }
            }

        }
    }
}

@Composable
private fun ProfileProperty(
    @StringRes key: Int,
    value: Any,
    icon: ImageVector,
    showDivider: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone à esquerda
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Textos Empilhados (Rótulo em cima, Valor em baixo)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(key),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 40.dp), // Alinha a linha com o texto, saltando o ícone
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenLightPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        ProfileScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenDarkPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        ProfileScreen()
    }
}