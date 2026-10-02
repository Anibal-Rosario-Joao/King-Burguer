package com.anibal.kingburguer.data

import com.anibal.kingburguer.api.KingBurguerService
import com.google.gson.Gson
import retrofit2.Response

class KingBurguerRepository (
    private val service: KingBurguerService,
    private val localStorage: KingBurguerLocalStorage
){

    // "Expor o UserCrentialsFlow" -> Get
    // val testFlow = localStorage.userCredetialsFlow
    suspend fun fetchInitialCredentials() = localStorage.fetchInitialUserCredential()

    suspend fun postUser(userRequest: UserRequest): ApiResult<UserCreateResponse>{
        val result = apiCall { service.postUser(userRequest) }
        return result
    }

    suspend fun login(
        loginRequest: LoginRequest,
        keepLogged: Boolean
    ): ApiResult<LoginResponse>{

        val result = apiCall{service.login(loginRequest)}
        //Em caso de sucesso guardar as credencias
        if (result is ApiResult.Success<LoginResponse>) {
            if (keepLogged) {
                updateCredencials(result.data)
            }
        }
        return result
    }


    suspend fun refreshToken(
        request: RefreshTokenRequest
    ): ApiResult<LoginResponse> {

        val userCredencials = localStorage.fetchInitialUserCredential()
        val token = "${userCredencials.tokenTypes} ${userCredencials.accessToken}"
        val result = apiCall { service.refreshToken(request, token) }

        if (result is ApiResult.Success<LoginResponse>) {
            updateCredencials(result.data)
        }
            return result
    }

    private suspend fun <T> apiCall(
        call: suspend () -> Response<T>
    ): ApiResult<T>{
        try {
            val response = call() // depois
            if(!response.isSuccessful){
                val errorData = response.errorBody()?.string()?.let { json ->
                    if (response.code() == 401) {
                        //401 -> Unaothorized (Falha)
                        val errorAuth = Gson().fromJson(json, ErrorAuth::class.java)
                        ApiResult.Error(errorAuth.detail.message)
                    } else {
                        Gson().fromJson(json, ApiResult.Error::class.java)
                    }
                }
                return errorData ?: ApiResult.Error("internal server error")
            }else{
                // 200 -> OK sucesso (Sucess)
                val data = response.body()

                if (data == null){
                    return ApiResult.Error("unexpected response success")
                }


                return ApiResult.Success(data)
            }

        }catch (e: Exception){
            return ApiResult.Error(e.message ?: "unexpetected exception") // depois
        }
    }

    private suspend fun updateCredencials(data: LoginResponse){
        //Em caso de sucesso guardar as nova credencias
        val newUserCredentials = UserCredencials(
            data.accessToken,
            data.refreshToken,
            data.expiresSeconds.toLong(),
            data.tokenType
        )
        localStorage.updateUserCredential(newUserCredentials)
    }
}