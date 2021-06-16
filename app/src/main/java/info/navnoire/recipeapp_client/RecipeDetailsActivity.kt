package info.navnoire.recipeapp_client

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import info.navnoire.recipeapp_client.ui.fragments.recipe.RecipeDetailsViewModel

class RecipeDetailsActivity : AppCompatActivity() {
    private val args : RecipeDetailsActivityArgs by navArgs()
    private val viewModel : RecipeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.recipe_nav_host_container) as NavHostFragment
        navHostFragment.navController.setGraph(R.navigation.recipe_details_flow_navigation, args.toBundle())

        viewModel.getRecipe(args.recipeId).observe(this, Observer {
            viewModel.warmImageCache(it)
        })
    }

    
}