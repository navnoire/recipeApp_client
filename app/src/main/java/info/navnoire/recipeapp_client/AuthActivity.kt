package info.navnoire.recipeapp_client

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import info.navnoire.recipeapp_client.ui.fragments.auth.AuthViewModel

private const val TAG = "AuthActivity"

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val viewModel: AuthViewModel by viewModels()

        viewModel.refreshTokenFlow.asLiveData().observe(this, { token ->
            Log.i(TAG, "onCreate: $token")
            if (token != null) {
                Intent(this, CategoryActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    it.putExtra("categoryId", 0)
                    startActivity(it)
                }
            } else {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.auth_nav_host_container) as NavHostFragment
                navHostFragment.navController.setGraph(R.navigation.auth_flow_navigation)
            }
        })
    }
}