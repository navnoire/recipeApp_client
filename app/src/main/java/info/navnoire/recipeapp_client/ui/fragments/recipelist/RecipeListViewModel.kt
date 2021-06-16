package info.navnoire.recipeapp_client.ui.fragments.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import info.navnoire.recipeapp_client.data.RecipeShortModel
import info.navnoire.recipeapp_client.data.source.remote.RecipeRepository
import kotlinx.coroutines.flow.Flow
import info.navnoire.recipeapp_client.ui.fragments.recipelist.RecipeListFilterType.ALL_RECIPES
import info.navnoire.recipeapp_client.ui.fragments.recipelist.RecipeListFilterType.BY_CATEGORY
import info.navnoire.recipeapp_client.ui.fragments.recipelist.RecipeListFilterType.BY_NAME

class RecipeListViewModel : ViewModel() {
    private val recipeRepository = RecipeRepository()

    fun fetchRecipeList(filter : RecipeListFilterType, categoryId : Int = 0, searchString : String = "") : Flow<PagingData<RecipeShortModel>> {
        return when(filter) {
            ALL_RECIPES -> recipeRepository.fetchAllRecipesList()
            BY_CATEGORY -> recipeRepository.fetchRecipesByCategory(categoryId)
            BY_NAME -> recipeRepository.fetchRecipesBySearchString(searchString)
        }.cachedIn(viewModelScope)
    }
}