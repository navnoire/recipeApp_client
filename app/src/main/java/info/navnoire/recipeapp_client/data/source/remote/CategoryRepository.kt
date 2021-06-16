package info.navnoire.recipeapp_client.data.source.remote

import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.data.Result
import info.navnoire.recipeapp_client.networking.CategoryService
import info.navnoire.recipeapp_client.networking.RetrofitClient

class CategoryRepository {
    private val categoryService = RetrofitClient.getClient().create(CategoryService::class.java)

    suspend fun fetchChildCategories(parentId: Int): Result<List<CategoryModel>> =
        CategoryListSource(categoryService).getChildCategories(parentId)

}