package info.navnoire.recipeapp_client

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import dagger.hilt.android.AndroidEntryPoint
import info.navnoire.recipeapp_client.ui.fragments.auth.AuthViewModel
import info.navnoire.recipeapp_client.ui.fragments.categorylist.CategoryListViewModel
import info.navnoire.recipeapp_client.ui.fragments.recipelist.RecipeListFilterType


private const val TAG = "CategoryActivity"

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private val viewModel: CategoryListViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val navArgs: MainFlowNavigationArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getCategoryList(navArgs.categoryId)
        viewModel.currentCategory.observe(this, { model ->
            if (model.hasChild) {
                if (model.parent == 0) {
                    Intent(this, CategoryActivity::class.java).also {
                        it.putExtra("categoryId", model.id)
                        startActivity(it)
                    }
                } else {
                    viewModel.getCategoryList(model.id)
                }
            } else {
                title = model.title
                val bundle = bundleOf(
                    "categoryId" to model.id,
                    "filterType" to RecipeListFilterType.BY_CATEGORY,
                )
                findNavController(R.id.main_nav_host_container).navigate(
                    R.id.action_categoryListFragment_to_recipeListFragment,
                    bundle,
                )
            }
        })

        authViewModel.refreshTokenFlow.asLiveData().observe(this, { token ->
            if (token == null) {
                Intent(this, AuthActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        title = getString(R.string.app_name)
    }
}



