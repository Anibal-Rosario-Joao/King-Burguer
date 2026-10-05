package com.anibal.kingburguer.data

import com.anibal.kingburguer.api.KingBurguerService
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
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

    suspend fun fetchFeed(): ApiResult<FeedResponse>{
        val userCredencials = localStorage.fetchInitialUserCredential()
        val token = "${userCredencials.tokenTypes} ${userCredencials.accessToken}"
        return apiCall { service.fetchFeed(token) }

    }

    suspend fun fetcnMe(): ApiResult<ProfileResponse>{
        val userCredencials = localStorage.fetchInitialUserCredential()
        val token = "${userCredencials.tokenTypes} ${userCredencials.accessToken}"
        return apiCall { service.fetchMe(token) }
    }

    suspend fun fetchProductById(productId: Int): ApiResult<ProductDetailResponse>{
        val userCredencials = localStorage.fetchInitialUserCredential()
        val token = "${userCredencials.tokenTypes} ${userCredencials.accessToken}"
        return apiCall { service.fetchProductById(token, productId) }
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

    suspend fun createCoupon(productId: Int): ApiResult<CouponResponse>{
        val userCredencials = localStorage.fetchInitialUserCredential()
        val token = "${userCredencials.tokenTypes} ${userCredencials.accessToken}"
        return  apiCall { service.createCoupon( token, productId) }
    }

    suspend fun fetchCoupons(page: Int, expired: Boolean?): ApiResult<List<ListCuponResponse>> {
        return try {
            val userCredentials = localStorage.fetchInitialUserCredential()
            val token = "${userCredentials.tokenTypes} ${userCredentials.accessToken}"
            val response = service.fetchCoupons(token, page, expired)
            if (response.isSuccessful) {
                val coupons = response.body()?.data ?: emptyList()
                ApiResult.Success(coupons)
            } else {
                ApiResult.Error("Erro ao buscar cupões")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Erro desconhecido")
        }
    }

    suspend fun fetchHighlight(): ApiResult<HighlightProductResponse>{
        val userCredentials = localStorage.fetchInitialUserCredential()
        val token = "${userCredentials.tokenTypes} ${userCredentials.accessToken}"
        return apiCall { service.fetchHighlight(token) }
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
                        try {
                            val errorAuth = Gson().fromJson(json, ErrorAuth::class.java)
                            ApiResult.Error(errorAuth.detail.message)
                        }catch (e: JsonSyntaxException){
                            val error = Gson().fromJson(json, Error::class.java)
                            ApiResult.Error(error.detail)
                        }
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