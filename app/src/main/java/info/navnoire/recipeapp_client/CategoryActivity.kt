package info.navnoire.recipeapp_client

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.ui.fragments.categorylist.CategoryListViewModel
import info.navnoire.recipeapp_client.ui.fragments.recipelist.RecipeListFilterType


private const val TAG = "CategoryActivity"

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private val viewModel: CategoryListViewModel by viewModels()
    private var restart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getCategoryList(0)
        viewModel.currentCategory.observe(this, {
            if (it.hasChild) {
                restart = it.id != 0
                viewModel.getCategoryList(it.id)
            } else {
                val bundle = bundleOf(
                    "categoryId" to it.id,
                    "filterType" to RecipeListFilterType.BY_CATEGORY
                )
                findNavController(R.id.main_nav_host_container).navigate(
                    R.id.recipeListFragment,
                    bundle,
                )
            }
        })
    }


    override fun onBackPressed() {
        if (!findNavController(R.id.main_nav_host_container).popBackStack()) finish()
        else if (restart) viewModel.setParentCategory(
            CategoryModel(
                0,
                "",
                true
            )
        )
    }
}
