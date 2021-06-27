package info.navnoire.recipeapp_client.networking

import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {
    companion object {
        private const val BASE_URL = "https://navnoire.info/RecipeApp/api/"
        private var retrofit: Retrofit? = null

        fun getClient(authenticator: Authenticator? = null): Retrofit =
            retrofit ?: Retrofit.Builder()
                .client(getOkhttpClient(authenticator))
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        fun getOkhttpClient(authenticator: Authenticator? = null): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("Accept", "application/json")
                    }.build())
                }.also { client ->
                    authenticator?.let { client.authenticator(it) }
                }.build()
        }
    }
}
