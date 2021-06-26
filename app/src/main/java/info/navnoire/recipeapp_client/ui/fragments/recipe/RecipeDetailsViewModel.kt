package info.navnoire.recipeapp_client.ui.fragments.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import info.navnoire.recipeapp_client.data.RecipeFullModel
import info.navnoire.recipeapp_client.data.repository.RecipeRepository
import info.navnoire.recipeapp_client.networking.Result
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _singleRecipeData: MutableLiveData<Result<RecipeFullModel>> = MutableLiveData()
    val singleRecipeData: LiveData<Result<RecipeFullModel>>
        get() = _singleRecipeData

    fun loadRecipe(recipeId: Int)  = viewModelScope.launch{
        _singleRecipeData.value = Result.Loading
        _singleRecipeData.value = repository.fetchSingleRecipe(recipeId = recipeId)
    }

    fun warmImageCache(recipe: RecipeFullModel) {
        val picasso = Picasso.get()
        recipe.steps.mapNotNull { it.stepImageUrl }.forEach {
            picasso.load(it).fetch()
        }
    }
}