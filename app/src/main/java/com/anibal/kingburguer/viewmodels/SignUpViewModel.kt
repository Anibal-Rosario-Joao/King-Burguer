package com.anibal.kingburguer.viewmodels

import android.icu.text.SimpleDateFormat
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.ParseException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anibal.kingburguer.compose.signup.FieldState
import com.anibal.kingburguer.compose.signup.FormState
import com.anibal.kingburguer.compose.signup.SignUpState
import com.anibal.kingburguer.validation.EmailValidator
import com.anibal.kingburguer.validation.Mask
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

class SignUpViewModel: ViewModel() {
    //Ocioso
    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    var formState by mutableStateOf(FormState())

    private val emailValidator = EmailValidator()

    fun updateName(newName: String){

        if (newName.isBlank()){
            formState = formState.copy(
                name = FieldState(field = newName, error = "O campo não pode ser vazio")
            )
            return
        }
        if (newName.length < 3){
            formState = formState.copy(
                name = FieldState(field = newName, error = "O campo deve ter pelo menos 3 caracter")
            )
            return
        }
        // Aqui é o sucesso
        formState = formState.copy(
            name = FieldState(field = newName, error = null)
        )
    }

    fun updatePassword(newPassword: String){
        if (newPassword.isBlank()){
            formState = formState.copy(
                password = FieldState(field = newPassword, error = "O campo não pode ser vazio")
            )
            return
        }
        if (newPassword.length < 8){
            formState = formState.copy(
                password = FieldState(field = newPassword, error = "O campo deve ter pelo menos 8 caracter")
            )
            return
        }
        // Aqui é o sucesso
        formState = formState.copy(
            password = FieldState(field = newPassword, error = null)
        )
    }

    fun updateConfirmPassaword(newConfirmPassword: String){

        if (newConfirmPassword.isBlank()){
            formState = formState.copy(
                confirmPassword = FieldState(field = newConfirmPassword, error = "O campo não pode ser vazio")
            )
            return
        }
        if (formState.password.field != newConfirmPassword){
            formState = formState.copy(
                confirmPassword = FieldState(field = newConfirmPassword, error = "O senha não coincide")
            )
            return
        }
        // Aqui é o sucessoe
        formState = formState.copy(
            confirmPassword = FieldState(field = newConfirmPassword, error = null)
        )
    }

    fun updateEmail(newEmail: String){
        val textString = emailValidator.validate(newEmail)
        // Aqui é o sucesso
        formState = formState.copy(
            email = FieldState(field = newEmail, error = textString
             )
        )
    }

    fun updateDocument(newDocument: String){
        val pattern = "###.###.###-##"
        val currentDocumment = formState.document.field
        val result = Mask(pattern,currentDocumment, newDocument)

        if (newDocument.isBlank()){
            formState = formState.copy(
                document = FieldState(field = result, error = "O campo não pode ser vazio")
            )
            return
        }

        if (result.length != pattern.length){
            formState = formState.copy(
                document = FieldState(field = result, error = "NUIT Inválido")
            )
            return
        }
        formState = formState.copy(
            document = FieldState(field = result, error = null)
        )
    }
    fun reset(){
        _uiState.update {
            SignUpState()
        }
    }

    fun updateBirthday(newBirthday: String){
        val pattern = "##/##/####"
        val currentBirthday = formState.birthday.field
        val result = Mask(pattern,currentBirthday, newBirthday)

        if (newBirthday.isBlank()){
            formState = formState.copy(
                birthday = FieldState(field = result, error = "O campo não pode ser vazio")
            )
            return
        }

        // o numero precisa ser igual da mascara = invalida
        if(result.length != pattern.length){
            formState = formState.copy(
                birthday = FieldState(field = result, error = "Data de nascimento inválida")
            )
            return
        }

        //Não validar a data 30/02/2000
        try {
            val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).run {
                isLenient = false //  Não deixa pegar datas aproximada ou por aproximação
                parse(result)
            }?.also {
                //Não validar a data futura
                val now = Date()
                if (it.after(now)){
                    formState = formState.copy(
                        birthday = FieldState(field = result, error = "Data de nascimento não ser maior que hoje")
                    )
                    return
                }
            }
            formState = formState.copy(
                birthday = FieldState(field = result, error = null)
            )

        }catch (e: ParseException){
            //Não validar a data 30/02/2000
            formState = formState.copy(
                birthday = FieldState(field = result, error = "Data de nascimento inválida")
            )
            return
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
           // _uiState.update { it.copy(isLoading = false, error = "Usuario não encontrado!") }
        }
    }
}