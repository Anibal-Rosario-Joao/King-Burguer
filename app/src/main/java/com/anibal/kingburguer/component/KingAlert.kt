package com.anibal.kingburguer.component

import android.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun KingAlert(
    onDismissRequest: () -> Unit,
    confirmationButton: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector
){
    AlertDialog(
        icon = {
            Icon(
            imageVector = icon,
            contentDescription = "Icon do Alerta" // TODO res
        )
               },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {},
        confirmButton = {
            TextButton(
                onClick = confirmationButton

            ){
                Text(text = stringResource(R.string.ok))
            }
        }

    )
}

@Preview(showBackground = true)
@Composable
fun KingAlertLightPreview() {
    KingBurguerTheme (dynamicColor = false){
        KingAlert(
            onDismissRequest = {},
            confirmationButton = {},
            dialogTitle = "King Burguer",
            dialogText = "Usuario não encotrado!",
            icon = Icons.Filled.Info
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KingAlertDarkPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = true){
        KingAlert(
            onDismissRequest = {},
            confirmationButton = {},
            dialogTitle = "King Burguer",
            dialogText = "Usuario não encotrado!",
            icon = Icons.Filled.Info
            )
    }
}
