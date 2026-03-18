package com.anibal.kingburguer.component


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun KingButton(
    text: String,
    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit

){
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            enabled = enabled && !loading,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if(!loading) {
                Text(
                    text = text.uppercase()
                )
            }
        }

        //Circulo de Progressão
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .scale(0.7f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KingButtonPreview() {
    KingBurguerTheme(dynamicColor=false) {
        Column() {
            // loading is FALSE
            KingButton("Hello World", enabled = false) {}
            // loading is FALSE
            KingButton("Hello World", enabled = true){}
            // enable is FALSE
            KingButton("Hello World", loading = true){}
            // enable is FALSE
            KingButton("Hello World", loading = false){}
        }
    }
}