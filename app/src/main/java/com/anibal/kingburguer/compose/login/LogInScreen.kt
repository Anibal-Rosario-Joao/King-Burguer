package com.anibal.kingburguer.compose.login



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.anibal.kingburguer.R
import com.anibal.kingburguer.component.KingAlert
import com.anibal.kingburguer.component.KingButton
import com.anibal.kingburguer.component.KingTextField
import com.anibal.kingburguer.component.KingTextTitle
import com.anibal.kingburguer.compose.Screen
import com.anibal.kingburguer.ui.theme.KingBurguerTheme
import com.anibal.kingburguer.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        val scrollState = rememberScrollState()
        var passwordHidden by remember { mutableStateOf(true) }

        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top)
            ) {

                uiState.error?.let {
                    KingAlert(
                        onDismissRequest = {
                           //TODO
                        },
                        confirmationButton = {
                            viewModel.reset()
                        },
                        dialogTitle = stringResource(R.string.app_name),
                        dialogText = uiState.error!!,
                        icon = Icons.Filled.Info
                    )
                }

                KingTextTitle(
                    text = stringResource(R.string.login)
                )

                KingTextField(
                    value = viewModel.email,
                    label = R.string.email,
                    placeholder = R.string.hint_email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ) {

                }

                KingTextField(
                    value = viewModel.password,
                    label = R.string.password,
                    placeholder = R.string.hint_password,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    offuscate = passwordHidden,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordHidden = !passwordHidden
                            }
                        ) {
                            val icon = if (passwordHidden) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            }

                            val description = if (passwordHidden) {
                                stringResource(R.string.show_password)
                            } else {
                                stringResource(R.string.hide_password)
                            }
                            Icon(
                                imageVector = icon,
                                contentDescription = description
                            )
                        }
                    }
                ) {
                    //Evento de onValueChange()
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = {
                            //TODO
                        }
                    )
                    Text(
                        text = stringResource(R.string.remember_me)
                    )
                }

                KingButton(
                    text = stringResource(R.string.send),

                    ) {
                    //Evento de onClick()
                    viewModel.send()
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.have_account)
                    )

                    TextButton(
                        onClick = {
                            //TODO
                            navController.navigate(Screen.HOME.route)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.sign_up)
                        )
                    }
                }
            }

            Image(
                modifier = Modifier
                    .fillMaxSize(),
                alignment = Alignment.BottomCenter,
                painter = painterResource(R.drawable.cover3),
                contentDescription = stringResource(R.string.burguer)
            )


        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenLightPreview() {
    KingBurguerTheme (dynamicColor = false){
        LoginScreen(rememberNavController())
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenDarkPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = true){
        LoginScreen(rememberNavController())
    }
}