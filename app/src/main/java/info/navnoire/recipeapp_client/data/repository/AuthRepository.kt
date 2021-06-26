package info.navnoire.recipeapp_client.data.repository

import info.navnoire.recipeapp_client.data.source.AuthSource
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.networking.RetrofitClient
import info.navnoire.recipeapp_client.networking.api.AuthApi
import info.navnoire.recipeapp_client.networking.request.RefreshTokenRequest
import info.navnoire.recipeapp_client.networking.request.SigninRequest
import info.navnoire.recipeapp_client.networking.request.SignupRequest
import info.navnoire.recipeapp_client.networking.response.StatusResponse
import info.navnoire.recipeapp_client.networking.response.TokenResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(){
    private val authApi = RetrofitClient.getClient().create(AuthApi::class.java)

    suspend fun signin(request: SigninRequest) : Result<TokenResponse> {
        return AuthSource(authApi).signin(request)
    }

    suspend fun signup(request:SignupRequest) : Result<StatusResponse> {
        return AuthSource(authApi).signup(request)
    }

    suspend fun refreshToken(request: RefreshTokenRequest) : Result<TokenResponse> {
        return AuthSource(authApi).refreshAccessToken(request)
    }
}