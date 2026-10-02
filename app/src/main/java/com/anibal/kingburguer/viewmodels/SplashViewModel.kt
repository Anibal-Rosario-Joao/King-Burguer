package com.anibal.kingburguer.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anibal.kingburguer.api.KingBurguerService
import com.anibal.kingburguer.data.KingBurguerLocalStorage
import com.anibal.kingburguer.data.KingBurguerRepository
import com.anibal.kingburguer.data.LoginResponse
import com.anibal.kingburguer.data.RefreshTokenRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: KingBurguerRepository
): ViewModel() {

    private val _hasSessionState = MutableStateFlow<Boolean?>(null)
    val hasSession: StateFlow<Boolean?> = _hasSessionState

    // Se tem ACCESS TOKEN && esta VALIDO -> HOME Caso contrario -> LOGIN
    init {
        viewModelScope.launch {
            with(repository.fetchInitialCredentials()){
                _hasSessionState.value = when{
                    accessToken.isEmpty() -> false // nunca logou no sistema
                    System.currentTimeMillis() < expiresTimestamp -> true // esta ativo vai para home
                    else -> {
                        Log.d("SplashViewModel", "Token Expirado, tentsndo fazer refresh")
                        val response = repository.refreshToken(RefreshTokenRequest(refreshToken))
                        when(response){
                            is LoginResponse.Sucess -> true
                            else -> false
                        }
                    }
                }
            }
        }
    }


    companion object{
        val factory = viewModelFactory {
            initializer {
                // pegar o context da aplicacao que esta a rodar
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStorage(application)
                val repository = KingBurguerRepository(service, localStorage)
                SplashViewModel(repository)
            }
        }
    }


}