package info.navnoire.recipeapp_client.data.repository

import android.content.Context
import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.data.source.CategoryListSource
import info.navnoire.recipeapp_client.networking.api.CategoryApi
import info.navnoire.recipeapp_client.networking.RetrofitClient
import info.navnoire.recipeapp_client.networking.TokenAuthenticator
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    tokenAuthenticator: TokenAuthenticator){
    private val categoryApi = RetrofitClient.getClient(tokenAuthenticator).create(CategoryApi::class.java)

    suspend fun fetchChildCategories(parentId: Int): Result<List<CategoryModel>> =
        CategoryListSource(categoryApi).getChildCategories(parentId)
}