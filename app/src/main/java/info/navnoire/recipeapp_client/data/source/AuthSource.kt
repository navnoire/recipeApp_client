package info.navnoire.recipeapp_client.data.source

import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.networking.api.AuthApi
import info.navnoire.recipeapp_client.networking.request.RefreshTokenRequest
import info.navnoire.recipeapp_client.networking.request.SigninRequest
import info.navnoire.recipeapp_client.networking.request.SignupRequest
import info.navnoire.recipeapp_client.networking.response.StatusResponse
import info.navnoire.recipeapp_client.networking.response.TokenResponse

class AuthSource(private val authApi: AuthApi) : SafeApiCall {

    suspend fun signin(signinRequest: SigninRequest): Result<TokenResponse> =
        safeApiCall { authApi.login(signinRequest) }

    suspend fun signup(signupRequest: SignupRequest): Result<StatusResponse> =
        safeApiCall { authApi.register(signupRequest) }

    suspend fun refreshAccessToken(request: RefreshTokenRequest) : Result<TokenResponse> =
        safeApiCall { authApi.refreshAccessToken(request) }

}
