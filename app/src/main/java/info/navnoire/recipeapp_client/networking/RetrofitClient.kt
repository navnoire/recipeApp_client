package info.navnoire.recipeapp_client.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private const val BASE_URL = "https://navnoire.info/RecipeApp/api/"
        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit = retrofit ?: Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }
}
