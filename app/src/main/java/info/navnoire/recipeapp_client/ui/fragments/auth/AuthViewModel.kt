package info.navnoire.recipeapp_client.ui.fragments.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.navnoire.recipeapp_client.data.repository.AuthRepository
import info.navnoire.recipeapp_client.data.repository.UserPreferencesRepository
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.networking.request.SigninRequest
import info.navnoire.recipeapp_client.networking.response.TokenResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _signinResponse: MutableLiveData<Result<TokenResponse>> = MutableLiveData()
    val signinResponse: LiveData<Result<TokenResponse>>
        get() = _signinResponse

    val refreshTokenFlow = userPreferencesRepository.refreshTokenFlow

    fun signin(username: String, password: String) = viewModelScope.launch {
        _signinResponse.value = Result.Loading
        _signinResponse.value = authRepository.signin(SigninRequest(username, password))
    }

    fun saveAccessTokens(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateAccessToken(accessToken)
            userPreferencesRepository.updateRefreshToken(refreshToken)
        }
    }

}