package info.navnoire.recipeapp_client.ui.fragments.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import info.navnoire.recipeapp_client.data.RecipeFullModel
import info.navnoire.recipeapp_client.data.Result
import info.navnoire.recipeapp_client.data.source.remote.RecipeRepository
import kotlinx.coroutines.launch

class RecipeDetailsViewModel : ViewModel() {
    private val recipeRepository = RecipeRepository()
    val singleRecipeData: MutableLiveData<RecipeFullModel> = MutableLiveData()

    fun getRecipe(id: Int): LiveData<RecipeFullModel> {
        viewModelScope.launch {
            singleRecipeData.value = fetchRecipe(id)
        }
        return singleRecipeData
    }

    //TODO: обработка ошибок при загрузке рецепта
    private suspend fun fetchRecipe(id: Int): RecipeFullModel {
        return when (val result = recipeRepository.fetchSingleRecipe(id)) {
            is Result.Success<RecipeFullModel> -> result.data
            else -> RecipeFullModel(0, "", listOf(), listOf(), "")
        }
    }

    fun warmImageCache(recipe : RecipeFullModel) {
        val picasso = Picasso.get()
        recipe.steps.mapNotNull { it.stepImageUrl }.forEach {
            picasso.load(it).fetch()
        }
    }
}