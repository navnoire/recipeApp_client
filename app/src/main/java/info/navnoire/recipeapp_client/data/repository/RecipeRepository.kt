package info.navnoire.recipeapp_client.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import info.navnoire.recipeapp_client.data.RecipeFullModel
import info.navnoire.recipeapp_client.data.RecipeShortModel
import info.navnoire.recipeapp_client.data.source.RecipeListPagingSource
import info.navnoire.recipeapp_client.data.source.SingleRecipeSource
import info.navnoire.recipeapp_client.networking.AuthenticatedImageDownloader
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.networking.RetrofitClient
import info.navnoire.recipeapp_client.networking.TokenAuthenticator
import info.navnoire.recipeapp_client.networking.api.RecipeApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRepository @Inject constructor(
    tokenAuthenticator: TokenAuthenticator,
    authenticatedImageDownloader: AuthenticatedImageDownloader
) {
    init {
        if (!authenticatedImageDownloader.initialized)
            authenticatedImageDownloader.initPicasso(tokenAuthenticator)
    }

    private val recipeApi =
        RetrofitClient.getClient(tokenAuthenticator).create(RecipeApi::class.java)

    fun fetchAllRecipesList(): Flow<PagingData<RecipeShortModel>> {
        return Pager(PagingConfig(pageSize = 30, enablePlaceholders = false)) {
            RecipeListPagingSource(recipeApi)
        }.flow
    }

    fun fetchRecipesByCategory(categoryId: Int): Flow<PagingData<RecipeShortModel>> {
        return Pager(PagingConfig(pageSize = 30, enablePlaceholders = false)) {
            RecipeListPagingSource(recipeApi, categoryId = categoryId)
        }.flow
    }

    fun fetchRecipesBySearchString(searchString: String): Flow<PagingData<RecipeShortModel>> {
        return Pager(PagingConfig(pageSize = 30, enablePlaceholders = false)) {
            RecipeListPagingSource(recipeApi, searchString = searchString)
        }.flow
    }

    suspend fun fetchSingleRecipe(recipeId: Int): Result<RecipeFullModel> {
        return SingleRecipeSource(recipeApi).load(recipeId)
    }
}