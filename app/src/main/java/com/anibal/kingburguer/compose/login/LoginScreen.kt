package com.anibal.kingburguer.compose.login

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.anibal.kingburguer.R
import com.anibal.kingburguer.compose.Screen
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun LoginScreen(navController: NavHostController) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        val scrollState = rememberScrollState()
        var passwordHidden by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            KingTextField(
                value = "todo",
                label = R.string.email,
                placeholder = R.string.hint_email,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                modifier = Modifier.padding(horizontal = 20.dp)
            ){

            }

            KingTextField(
                value = "todo",
                label = R.string.password,
                placeholder = R.string.hint_password,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                offuscate = passwordHidden,
                modifier = Modifier.padding(horizontal = 20.dp),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordHidden = !passwordHidden
                        }
                    ) {
                        val icon = if (passwordHidden){
                            Icons.Filled.VisibilityOff
                        }else{
                            Icons.Filled.Visibility
                        }

                        val description = if (passwordHidden){
                            stringResource(R.string.show_password)
                        }else{
                            stringResource(R.string.hide_password)
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = description
                        )
                    }
                }
            ){

            }

            OutlinedButton(
                enabled = true, //TODO
                onClick = { navController.navigate(Screen.HOME.route)
                }
            ) {
                Text(
                    text = stringResource(R.string.send)
                )
            }



        }
    }

}

@Composable
fun KingTextField(
    value: String,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    offuscate: Boolean = false,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
){
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        maxLines = 1,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(label)
            )
        },
        placeholder = {
            Text(
                text = stringResource(placeholder)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        trailingIcon = trailingIcon,
        visualTransformation = if (offuscate)
            PasswordVisualTransformation()
        else
            VisualTransformation.None
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    KingBurguerTheme {
        LoginScreen(rememberNavController())
    }
}