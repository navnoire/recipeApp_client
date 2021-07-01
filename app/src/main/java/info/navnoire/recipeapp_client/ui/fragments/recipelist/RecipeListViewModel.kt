package info.navnoire.recipeapp_client.ui.fragments.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import info.navnoire.recipeapp_client.data.RecipeShortModel
import info.navnoire.recipeapp_client.data.repository.RecipeRepository
import info.navnoire.recipeapp_client.ui.fragments.recipelist.RecipeListFilterType.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    fun fetchRecipeList(
        filter: RecipeListFilterType,
        categoryId: Int = 0,
        searchString: String = ""
    ): Flow<PagingData<RecipeShortModel>> {
        return when (filter) {
            ALL_RECIPES -> recipeRepository.fetchAllRecipesList()
            BY_CATEGORY -> recipeRepository.fetchRecipesByCategory(categoryId)
            BY_NAME -> recipeRepository.fetchRecipesBySearchString(searchString)
        }.cachedIn(viewModelScope)
    }
}