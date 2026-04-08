package com.anibal.kingburguer.viewmodels

import TextString
import android.icu.text.SimpleDateFormat
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.ParseException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anibal.kingburguer.R
import com.anibal.kingburguer.compose.signup.FieldState
import com.anibal.kingburguer.compose.signup.FormState
import com.anibal.kingburguer.compose.signup.SignUpState
import com.anibal.kingburguer.textstring.ResourceString
import com.anibal.kingburguer.validation.BirthdayValidator
import com.anibal.kingburguer.validation.ConfirmPasswordValidator
import com.anibal.kingburguer.validation.DocumentValidator
import com.anibal.kingburguer.validation.EmailValidator
import com.anibal.kingburguer.validation.Mask
import com.anibal.kingburguer.validation.NameValidator
import com.anibal.kingburguer.validation.PasswordValidator
import com.anibal.kingburguer.validation.Validator
import com.anibal.kingburguer.validation.Validator2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

 class SignUpViewModel: ViewModel() {
    //Ocioso
    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    var formState by mutableStateOf(FormState())

     private val validator1 =  mapOf<String, Validator >(
         "Name" to NameValidator(),
         "Email" to EmailValidator(),
         "Password" to PasswordValidator(),
     )
     private val simpleValidator = ConfirmPasswordValidator()

     private val validator2 =  mapOf<String, Validator2>(
         "Birtday" to BirthdayValidator(),
         "Document" to DocumentValidator()
     )

/*
    private val validator =  mapOf<String,Validator>(
        "Email" to EmailValidator(),
        "Name" to NameValidator(),
        "Password" to PasswordValidator(),
        "Password2" to ConfirmPasswordValidator(),
        "Birtday" to BirthdayValidator(),
        "Document" to DocumentValidator()
    )






    fun updateConfirmPassaword(newConfirmPassword: String){
        val textString = validator["Password2"]?.validate(newConfirmPassword)
        formState = formState.copy(
            confirmPassword = FieldState(field = newConfirmPassword, error = textString)
        )
    }

    fun updateDocument(newDocument: String){
        val pattern = "###.###.###-##"
        val currentDocument = formState.document.field
        val result = Mask(pattern, currentDocument, newDocument)
        val textString = validator["Document"]?.validate(newDocument)

        formState = formState.copy(
            document = FieldState(field = result, error = textString)
        )
    }


    fun updateBirthday(newBirthday: String){
        val pattern = "##/##/####"
        val currentBirthday = formState.birthday.field
        val result = Mask(pattern,currentBirthday, newBirthday)
        val textString = validator["Birthday"]?.validate(newBirthday)

        formState = formState.copy(
            birthday = FieldState(field = result, error = textString)
        )

    }

 */
fun updateName(newName: String){
    val textString = validator1["Name"]?.validate(newName)
    formState = formState.copy(
        name = FieldState(field = newName, error = textString)
    )
}
fun updateEmail(newEmail: String){
    val textString = validator1["Email"]?.validate(newEmail)
    formState = formState.copy(
        email = FieldState(field = newEmail, error = textString
        )
    )
}
     fun updatePassword(newPassword: String){
         val textString = validator1["Password"]?.validate(newPassword)
         formState = formState.copy(
             password = FieldState(field = newPassword, error = textString)
         )
     }

     fun updateConfirmPassaword(newConfirmPassword: String){
         val password = formState.password.field
         val textString = simpleValidator.validate(newConfirmPassword, password)

         formState = formState.copy(
             confirmPassword = FieldState(field = newConfirmPassword, error = textString
             )
         )
     }

     fun updateDocument(newDocument: String){
         val pattern = "###.###.###-##"
         val currentDocument = formState.document.field
         val result = Mask(pattern, currentDocument, newDocument)
         val textString = validator2["Document"]?.validate(pattern,currentDocument,result)

         formState = formState.copy(
             document = FieldState(field = result, error = textString)
         )
     }

     fun updateBirthday(newBirthday: String){
         val pattern = "##/##/####"
         val currentBirthday = formState.birthday.field
         val result = Mask(pattern,currentBirthday, newBirthday)
         val textString = validator2["Birthday"]?.validate(pattern,currentBirthday,result)

         formState = formState.copy(
             birthday = FieldState(field = result, error = textString)
         )

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

    fun reset(){
        _uiState.update {
            SignUpState()
        }
    }
}

