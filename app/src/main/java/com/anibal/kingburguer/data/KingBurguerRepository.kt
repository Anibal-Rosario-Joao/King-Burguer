package com.anibal.kingburguer.data

import com.anibal.kingburguer.api.KingBurguerService
import com.google.gson.Gson

class KingBurguerRepository (
    private val service: KingBurguerService
){

    suspend fun postUser(userRequest: UserRequest): UserCreateResponse{
        // response -> Service
        val response = service.postUser(userRequest)
        val sucess = response.isSuccessful

        try {
            if(!sucess){
                val errorData = response.errorBody()?.string()?.let { json ->
                    //falha(ErrorAuth)
                    if(response.code() == 401){
                        // Serializacao or Descerializacao
                        Gson().fromJson(json, UserCreateResponse.ErrorAuth::class.java)
                    } else {
                        //falha(Error)
                        Gson().fromJson(json, UserCreateResponse.Error::class.java)
                    }
                }
                return errorData ?: UserCreateResponse.Error("internal server error")
            } // Como demos return antes, não precisamos da palavra else{}
            // sucesso (Sucess)
            val data = response.body()?.string()?.let { json ->
                Gson().fromJson(json, UserCreateResponse.Sucess::class.java)
            }
            return data ?: UserCreateResponse.Error("unexpected response success")

        }catch (e: Exception){
            return UserCreateResponse.Error(e.message ?: "unexpetected exception")
        }

    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse{
        // response -> Service
        val response = service.login(loginRequest)
        val sucess = response.isSuccessful

        try {
            if(!sucess){
                val errorData = response.errorBody()?.string()?.let { json ->
                    //401 -> Unaothorized (Falha)
                    Gson().fromJson(json, LoginResponse.ErrorAuth::class.java)
                }
                return errorData ?: LoginResponse.Error("internal server error")
            }else{
                // 200 -> OK sucesso (Sucess)
                val data = response.body()?.string()?.let { json ->
                    Gson().fromJson(json, LoginResponse.Sucess::class.java)
                }
                return data ?: LoginResponse.Error("unexpected response success")
            }

        }catch (e: Exception){
            return LoginResponse.Error(e.message ?: "unexpetected exception")
        }
    }
}