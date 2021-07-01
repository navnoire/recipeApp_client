package info.navnoire.recipeapp_client.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.ui.fragments.auth.LoginFragment

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null, length: Int) {
    val snackbar = Snackbar.make(this, message, length)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(error: Result.Error, action: (() -> Unit)? = null) {
    when {
        error.isNetworkError -> requireView().snackbar(
            message = "Please check your internet connection",
            action = action,
            length = Snackbar.LENGTH_INDEFINITE
        )
        error.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar(
                    message = "You've entered incorrect email or password",
                    length = Snackbar.LENGTH_LONG
                )
            } else {
                //todo: logout user - перелогинить
            }
        }
        else -> requireView().snackbar(
            message = error.errorBody?.string().toString(),
            length = Snackbar.LENGTH_LONG
        )
    }

}