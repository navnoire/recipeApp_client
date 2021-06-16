package info.navnoire.recipeapp_client.networking

import android.content.res.Resources
import info.navnoire.recipeapp_client.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private val BASE_URL = ""
        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit = retrofit ?: Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
