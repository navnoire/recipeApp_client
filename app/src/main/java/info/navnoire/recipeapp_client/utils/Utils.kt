package info.navnoire.recipeapp_client.utils

import android.content.Context
import android.view.View
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import info.navnoire.recipeapp_client.networking.RetrofitClient
import info.navnoire.recipeapp_client.networking.TokenAuthenticator

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

interface OnBackPressedListener {
    fun onBackPressed() : Boolean
}