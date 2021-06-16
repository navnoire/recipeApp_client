package info.navnoire.recipeapp_client.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import info.navnoire.recipeapp_client.data.RecipeFullModel
import info.navnoire.recipeapp_client.data.RecipeShortModel
import info.navnoire.recipeapp_client.networking.RetrofitClient
import info.navnoire.recipeapp_client.networking.RecipeService
import kotlinx.coroutines.flow.Flow
import info.navnoire.recipeapp_client.data.Result

class RecipeRepository {
    private val recipeService = RetrofitClient.getClient().create(RecipeService::class.java)

    fun fetchAllRecipesList(): Flow<PagingData<RecipeShortModel>> {
        return Pager(PagingConfig(pageSize = 30, enablePlaceholders = false)) {
            RecipeListPagingSource(recipeService)
        }.flow
    }

    fun fetchRecipesByCategory(categoryId : Int) : Flow<PagingData<RecipeShortModel>> {
        return Pager(PagingConfig(pageSize = 30, enablePlaceholders = false)) {
            RecipeListPagingSource(recipeService, categoryId = categoryId)
        }.flow
    }

    fun fetchRecipesBySearchString(searchString : String) : Flow<PagingData<RecipeShortModel>> {
        return Pager(PagingConfig(pageSize = 30, enablePlaceholders = false)) {
            RecipeListPagingSource(recipeService, searchString = searchString)
        }.flow
    }

    suspend fun fetchSingleRecipe(recipeId : Int): Result<RecipeFullModel> {
        return SingleRecipeSource(recipeService).load(recipeId)
    }
}