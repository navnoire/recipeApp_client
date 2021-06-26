package info.navnoire.recipeapp_client

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import info.navnoire.recipeapp_client.ui.fragments.auth.AuthViewModel

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val viewModel: AuthViewModel by viewModels()

        viewModel.refreshTokenFlow.asLiveData().observe(this, { token ->
            if (token != null) {
                Intent(this, CategoryActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }
}