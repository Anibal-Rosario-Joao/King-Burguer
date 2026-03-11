package com.anibal.kingburguer.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anibal.kingburguer.compose.login.LoginUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    //Ocioso
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun reset(){
        _uiState.update {
            LoginUiState()
        }
    }

    fun send(){
        // depod os dados vão para o server
        _uiState.update {
            //Carregando
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            delay(3000)
            //Sucesso
            _uiState.update { it.copy(isLoading = false,goToHome = true) }

            //Falha
           // _uiState.update { it.copy(isLoading = false, error = "Usuario não encontrado") }
        }
    }
}