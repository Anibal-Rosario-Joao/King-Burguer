package com.anibal.kingburguer.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun KingButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit

){
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text.uppercase()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KingButtonPreview() {
    KingBurguerTheme {
        KingButton("Hello World"){

        }
    }
}