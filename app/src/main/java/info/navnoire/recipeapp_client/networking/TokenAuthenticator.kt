package info.navnoire.recipeapp_client.networking

import info.navnoire.recipeapp_client.data.repository.AuthRepository
import info.navnoire.recipeapp_client.data.repository.UserPreferencesRepository
import info.navnoire.recipeapp_client.networking.request.RefreshTokenRequest
import info.navnoire.recipeapp_client.networking.response.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val authRepository: AuthRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            withContext((Dispatchers.IO)) {
                when (val tokenResponse = getUpdatedToken()) {
                    is Result.Success -> {
                        userPreferencesRepository.updateAccessToken(
                            tokenResponse.data.accessToken,
                        )
                        userPreferencesRepository.updateRefreshToken(
                            tokenResponse.data.refreshToken
                        )
                        response.request.newBuilder()
                            .header("Authorization", "Bearer ${tokenResponse.data.accessToken}")
                            .build()
                    }
                    //todo: очистить преференсы, перелогинить пользователя, рефреш токен истёк
                    else -> {
                        userPreferencesRepository.clear()
                        null
                    }
                }
            }
        }
    }

    private suspend fun getUpdatedToken(): Result<TokenResponse> {
        val refreshToken = userPreferencesRepository.refreshTokenFlow.first() ?: ""
        return authRepository.refreshToken(RefreshTokenRequest(refreshToken))
    }

}