package com.anibal.kingburguer.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anibal.kingburguer.api.KingBurguerService
import com.anibal.kingburguer.compose.login.FormState2
import com.anibal.kingburguer.compose.login.LoginUiState
import com.anibal.kingburguer.compose.signup.FieldState
import com.anibal.kingburguer.data.KingBurguerRepository
import com.anibal.kingburguer.data.LoginRequest
import com.anibal.kingburguer.data.LoginResponse
import com.anibal.kingburguer.data.UserCreateResponse
import com.anibal.kingburguer.data.UserRequest
import com.anibal.kingburguer.textstring.RawString
import com.anibal.kingburguer.validation.ConfirmPasswordValidator
import com.anibal.kingburguer.validation.EmailValidator
import com.anibal.kingburguer.validation.NameValidator
import com.anibal.kingburguer.validation.PasswordValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

open class LogInViewModel(
    private val repository: KingBurguerRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var formState by mutableStateOf(FormState2())

    private val validator = mapOf(
        "Email" to EmailValidator(),
        "Password" to PasswordValidator()
    )

    fun updateEmail(newEmail: String) {
        val textString = validator["Email"]?.validate(newEmail)
        formState = formState.copy(
            email = FieldState(field = newEmail, error = textString, isValid = textString == null)
        )
        updateButton()
    }

    fun updatePassword(newPassword: String) {
        val textString = validator["Password"]?.validate(newPassword)
        formState = formState.copy(
            password = FieldState(
                field = newPassword,
                error = textString,
                isValid = textString == null
            )
        )

        updateButton()
    }

    fun updateRememberMe(rememberMe: Boolean) {
        formState = formState.copy(
            rememberMe = rememberMe
        )
    }

    private fun updateButton() {
        val formIsValid = with(formState) {
            email.isValid &&
                    password.isValid
        }
        formState = formState.copy(
            formIsValid = formIsValid
        )
    }

    fun reset() {
        _uiState.update {
            LoginUiState()
        }
    }

    fun send() {
        _uiState.update {
            //Carregando
            it.copy(isLoading = true)
        }
        // depod os dados vão para o server
        // _uiState.update { it.copy(isLoading = true)}
        viewModelScope.launch {

            with(formState) {

                val loginRequest = LoginRequest(
                    username = email.field,
                    password = password.field,
                )

                val result = repository.login(loginRequest)
                Log.i("Teste", "result is $result")
                // grande poder da sealed class, que é tratar ela com WHEN

                when (result) {
                    is LoginResponse.Sucess -> {
                        _uiState.update { it.copy(isLoading = false, goToHome = true) }
                    }

                    is LoginResponse.ErrorAuth -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = RawString(result.detail.message)
                            )
                        }
                    }

                    is LoginResponse.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = RawString(result.detail)
                            )
                        }
                    }
                }
            }

        }
    }

    companion object{
        val factory = viewModelFactory {
            initializer {
                val service = KingBurguerService.create()
                val repository = KingBurguerRepository(service)
                LogInViewModel(repository)
            }
        }
    }
}