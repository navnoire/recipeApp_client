package info.navnoire.recipeapp_client.data.repository

import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.data.source.CategoryListSource
import info.navnoire.recipeapp_client.networking.api.CategoryApi
import info.navnoire.recipeapp_client.networking.RetrofitClient
import javax.inject.Inject

class CategoryRepository @Inject constructor(){
    private val categoryApi = RetrofitClient.getClient().create(CategoryApi::class.java)

    suspend fun fetchChildCategories(parentId: Int, token : String): Result<List<CategoryModel>> =
        CategoryListSource(categoryApi).getChildCategories(parentId, token)
}