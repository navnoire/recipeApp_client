package info.navnoire.recipeapp_client.data.source

import info.navnoire.recipeapp_client.networking.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * @author Belal Khan
 * @see <a href="https://github.com/probelalkhan/android-login-signup-tutorial/blob/dependency-injection/app/src/main/java/net/simplifiedcoding/data/network/SafeApiCall.kt">https://github.com/probelalkhan/android-login-signup-tutorial</a>
 */

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Result.Error(false,throwable.response()?.errorBody(), throwable.code())
                    }
                    else -> {
                        Result.Error(true, null, null)
                    }
                }
            }
        }
    }
}