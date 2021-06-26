package info.navnoire.recipeapp_client.data.source

import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.networking.api.CategoryApi
import info.navnoire.recipeapp_client.networking.response.CategoryData
import info.navnoire.recipeapp_client.networking.succeeded

class CategoryListSource(
    private val categoryApi: CategoryApi
) : SafeApiCall{

    suspend fun getChildCategories(parentId: Int, token : String): Result<List<CategoryModel>> =
        safeApiCall {
            val data = categoryApi.fetchChildCategories(parentId = parentId, token)
            convertToModel(data)
        }

    private fun convertToModel(data: List<CategoryData>): List<CategoryModel> {
        return data.map { category ->
            CategoryModel(
                id = category.id,
                title = category.title,
                hasChild = category.hasChild
            )
        }
    }
}

