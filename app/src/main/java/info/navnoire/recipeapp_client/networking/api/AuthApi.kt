package info.navnoire.recipeapp_client.networking.api

import info.navnoire.recipeapp_client.networking.request.RefreshTokenRequest
import info.navnoire.recipeapp_client.networking.request.SigninRequest
import info.navnoire.recipeapp_client.networking.request.SignupRequest
import info.navnoire.recipeapp_client.networking.response.StatusResponse
import info.navnoire.recipeapp_client.networking.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST(value = "auth/signin")
    suspend fun login(@Body body: SigninRequest) : TokenResponse

    @POST(value = "auth/signup")
    suspend fun register(@Body body: SignupRequest) : StatusResponse

    @POST(value = "auth/refresh")
    suspend fun refreshAccessToken(@Body body: RefreshTokenRequest) : TokenResponse
}