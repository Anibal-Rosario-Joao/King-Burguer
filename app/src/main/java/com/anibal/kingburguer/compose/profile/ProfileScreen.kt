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
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anibal.kingburguer.R
import com.anibal.kingburguer.common.formatted
import com.anibal.kingburguer.compose.product.ProductScreen
import com.anibal.kingburguer.compose.product.ProductUiState
import com.anibal.kingburguer.data.ProfileResponse
import com.anibal.kingburguer.ui.theme.KingBurguerTheme
import com.anibal.kingburguer.validation.Mask
import com.anibal.kingburguer.viewmodels.ProfileViewModel
import java.util.Date


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.factory)
){
    val state = viewModel.uiState.collectAsState().value
    ProfileScreen1(modifier, state)


}

@Composable
fun ProfileScreen1(
    modifier: Modifier,
    state: ProfileUiState
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(
                    //text = state.error
                    text = stringResource(R.string.erro_message),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }

            state.profile != null -> {
                ProfileScreen2(modifier, state.profile)
            }
        }
    }
}


@Composable
fun ProfileScreen2(
    modifier: Modifier,
    profile: ProfileResponse
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
            //    .padding(16.dp),
           // horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Meus Perfil",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 12.dp)
            )

                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        ProfileProperty(
                            R.string.prop_id,
                            profile.id.toString(),
                            Icons.Filled.Numbers
                        )
                        ProfileProperty(R.string.prop_name, profile.name, Icons.Filled.Person)
                        ProfileProperty(R.string.prop_email, profile.email, Icons.Filled.Email)
                        ProfileProperty(
                            R.string.prop_document,
                            Mask("###.###.###-##", "", profile.document),
                            Icons.Filled.DocumentScanner
                        )
                        ProfileProperty(
                            R.string.prop_birthday,
                            profile.birthday.formatted(),
                            Icons.Filled.Cake,
                            showDivider = false
                        )
                    }
                }


        }

    }
}

@Composable
private fun ProfileProperty(
    @StringRes key: Int,
    value: String,
    icon: ImageVector,
    showDivider: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = stringResource(key),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
    if (showDivider) {
        HorizontalDivider(
            modifier = Modifier.padding(start = 40.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}
