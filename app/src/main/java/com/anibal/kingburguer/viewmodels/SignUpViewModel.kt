package com.anibal.kingburguer.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anibal.kingburguer.api.KingBurguerService
import com.anibal.kingburguer.compose.signup.FieldState
import com.anibal.kingburguer.compose.signup.FormState
import com.anibal.kingburguer.compose.signup.SignUpState
import com.anibal.kingburguer.data.KingBurguerRepository
import com.anibal.kingburguer.data.UserCreateResponse
import com.anibal.kingburguer.data.UserRequest
import com.anibal.kingburguer.textstring.RawString
import com.anibal.kingburguer.validation.BirthdayValidator
import com.anibal.kingburguer.validation.ConfirmPasswordValidator
import com.anibal.kingburguer.validation.DocumentValidator
import com.anibal.kingburguer.validation.EmailValidator
import com.anibal.kingburguer.validation.NameValidator
import com.anibal.kingburguer.validation.PasswordValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Locale

class SignUpViewModel(
    private val repository: KingBurguerRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    var formState by mutableStateOf(FormState())

     private val validator =  mapOf(
         "Name" to NameValidator(),
         "Email" to EmailValidator(),
         "Password" to PasswordValidator(),
         "ConfirmPassword" to ConfirmPasswordValidator(),
     )
     val birthdayValidator = BirthdayValidator()
     val documentValidator = DocumentValidator()

     fun updateName(newName: String){
         val textString = validator["Name"]?.validate(newName)
         formState = formState.copy(
             name = FieldState(field = newName, error = textString, isValid = textString == null)
         )
         updateButton()
     }
     fun updateEmail(newEmail: String){
         val textString = validator["Email"]?.validate(newEmail)
         formState = formState.copy(
             email = FieldState(field = newEmail, error = textString, isValid = textString == null)
         )
         updateButton()
     }
     fun updatePassword(newPassword: String){
         val confirmPassword2 = formState.confirmPassword.field
         var textString = validator["Password"]?.validate(newPassword)
         formState = formState.copy(
             password = FieldState(field = newPassword, error = textString, isValid = textString == null)
         )
         textString = validator["ConfirmPassword"]?.validate(newPassword, confirmPassword2)
         formState = formState.copy(
             confirmPassword = FieldState(field = confirmPassword2, error = textString, isValid = textString == null)
         )
         updateButton()
     }

     fun updateConfirmPassaword(newConfirmPassword: String){
         val password1 = formState.password.field
         var textString = validator["confirmPassword"]?.validate(password1, newConfirmPassword)

         formState = formState.copy(
             confirmPassword = FieldState(field = newConfirmPassword, error = textString, isValid = textString == null)
         )
         textString = validator["Password"]?.validate(password1, newConfirmPassword)
         formState = formState.copy(
             password = FieldState(field = password1, error = textString, isValid = textString == null)
         )
         updateButton()
     }

     fun updateDocument(newDocument: String){
         val currentDocument = formState.document.field
         val textString = documentValidator.validate(currentDocument,newDocument)

         formState = formState.copy(
             document = FieldState(field = documentValidator.result, error = textString, isValid = textString == null)
         )
         updateButton()
     }

     fun updateBirthday(newBirthday: String){
         val currentBirthday = formState.birthday.field
         val textString = birthdayValidator.validate(currentBirthday,newBirthday)

         formState = formState.copy(
             birthday = FieldState(field = birthdayValidator.result, error = textString, isValid = textString == null)
         )
         updateButton()
     }

     private fun updateButton(){
        val formIsValid = with(formState){
             name.isValid &&
             email.isValid &&
             password.isValid &&
             confirmPassword.isValid &&
             document.isValid &&
             birthday.isValid
         }
         formState = formState.copy(
             formIsValid = formIsValid
         )
     }
     fun send(){
         _uiState.update {
             //Carregando
             it.copy(isLoading = true)
         }
        // depod os dados vão para o server
       // _uiState.update { it.copy(isLoading = true)}
        viewModelScope.launch {

            with(formState) {
                val date = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
                    .parse(birthday.field)

                val dataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(date!!)

                val documentFormatted = document.field.filter { it.isDigit() }

                val userRequest = UserRequest(
                    name = name.field,
                    email = email.field,
                    password = password.field,
                    document = documentFormatted,
                    birthday = dataFormat
                )

                val result = repository.postUser(userRequest)
                Log.i("Teste", "result is $result")
                // grande poder da sealed class, que é tratar ela com WHEN

                when(result){
                    is UserCreateResponse.Sucess -> {
                        _uiState.update{ it.copy(isLoading = false, goToLogin = true) }
                    }
                    is UserCreateResponse.ErrorAuth -> {
                        _uiState.update { it.copy(isLoading = false, error = RawString(result.detail.message ))}
                    }
                    is UserCreateResponse.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = RawString(result.detail))}
                    }
                }
            }
        }
    }
    fun reset(){
        _uiState.update { SignUpState() }
    }

    companion object{
        val factory = viewModelFactory {
            initializer {
                val service = KingBurguerService.create()
                val repository = KingBurguerRepository(service)
                SignUpViewModel(repository)
            }
        }
    }
}

