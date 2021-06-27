package info.navnoire.recipeapp_client.networking

import android.content.Context
import android.util.Log
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "AuthImageDownload"

@Singleton
class AuthenticatedImageDownloader @Inject constructor(@ApplicationContext private val context: Context) {
    var initialized : Boolean = false

    fun initPicasso(tokenAuthenticator: TokenAuthenticator) {
        val instance = Picasso.Builder(context)
            .downloader(OkHttp3Downloader(RetrofitClient.getOkhttpClient(tokenAuthenticator)))
            .build()
        Picasso.setSingletonInstance(instance)
        initialized = true
        Log.i(TAG, "initPicasso: picasso initialized")
    }
}