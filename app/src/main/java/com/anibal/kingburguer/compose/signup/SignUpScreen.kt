package com.anibal.kingburguer.compose.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
import com.anibal.kingburguer.ui.theme.KingBurguerTheme
import com.anibal.kingburguer.viewmodels.SignUpViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    navController:  NavHostController,
    onNavigationClick: () -> Unit,
    onNavigateToHome: () -> Unit
){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.login))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onNavigationClick
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    scrollBehavior = scrollBehavior
                )
            }
        ) { contentPadding ->

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = contentPadding.calculateTopPadding())

            ) {

                SignUpContentScreen(
                    viewModel = viewModel,
                    navController = navController,
                    onNavigationClick = onNavigationClick,
                    onNavigateToHome = onNavigateToHome
                )
            }
        }

}

@Composable
private fun SignUpContentScreen(
    viewModel : SignUpViewModel,
    navController: NavHostController,
    onNavigationClick: () -> Unit,
    onNavigateToHome: () -> Unit
){
        val scrollState = rememberScrollState()
        var passwordHidden by remember { mutableStateOf(true) }

        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top)
            ) {
                // Importanta para o uso do botao de voltar Empilhamento de telas
                LaunchedEffect(key1 = uiState.goToHome) {
                    if(uiState.goToHome){
                        onNavigateToHome()
                        viewModel.reset()
                    }
                }

                if(uiState.goToHome){
                    onNavigateToHome()
                }
                uiState.error?.let {
                    KingAlert(
                        onDismissRequest = {
                            //TODO
                        },
                        confirmationButton = {
                            viewModel.reset()
                        },
                        dialogTitle = stringResource(R.string.app_name),
                        dialogText = uiState.error!!.asString(),
                        icon = Icons.Filled.Info
                    )
                }
                KingTextTitle(
                    text = stringResource(R.string.sign_up)
                )
                KingTextField(
                    value = viewModel.formState.name.field,
                    label = R.string.name,
                    placeholder = R.string.hint_name,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    error = viewModel.formState.name.error?.asString()
                ) { labelName ->
                    viewModel.updateName(labelName)
                }

                KingTextField(
                    value =  viewModel.formState.email.field,
                    label = R.string.email,
                    placeholder = R.string.hint_email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    error = viewModel.formState.email.error?.asString()
                ) { labelEmail ->
                    viewModel.updateEmail(labelEmail)
                }

                KingTextField(
                    value = viewModel.formState.password.field,
                    label = R.string.password,
                    placeholder = R.string.hint_password,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    error = viewModel.formState.password.error?.asString(),
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
                ) { labelPassword ->
                    viewModel.updatePassword(labelPassword)
                }

                KingTextField(
                    value = viewModel.formState.confirmPassword.field,
                    label = R.string.confirm_password,
                    placeholder = R.string.hint_confirm_password,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    error = viewModel.formState.confirmPassword.error?.asString(),
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
                ) { labelConfirmPassword ->
                        viewModel.updateConfirmPassaword(labelConfirmPassword)
                }

                KingTextField(
                    value = TextFieldValue(
                        text = viewModel.formState.document.field,
                        selection = TextRange(viewModel.formState.document.field.length)
                    ),
                    label = R.string.document,
                    placeholder = R.string.hint_document,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                    error = viewModel.formState.document.error?.asString()
                ) { textFieldValue ->
                    viewModel.updateDocument(textFieldValue.text)
                }

                KingTextField(
                    value = TextFieldValue(
                        text = viewModel.formState.birthday.field,
                        selection = TextRange(viewModel.formState.birthday.field.length)
                    ),
                    label = R.string.birthday,
                    placeholder = R.string.hint_birthday,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ) { textFieldValue ->
                    viewModel.updateBirthday(textFieldValue.text)
                }

                KingButton(
                    text = stringResource(R.string.send),
                    enabled = true,
                    loading = uiState.isLoading,
                    onClick = {
                        viewModel.send()
                    }

                    )
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpnScreenLightPreview() {
    KingBurguerTheme (dynamicColor = false){
        SignUpScreen(
            navController = rememberNavController(),
            onNavigationClick = {},
            onNavigateToHome = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenDarkPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = true){
        SignUpScreen(
            navController = rememberNavController(),
            onNavigationClick = {},
            onNavigateToHome = {}
        )
    }
}