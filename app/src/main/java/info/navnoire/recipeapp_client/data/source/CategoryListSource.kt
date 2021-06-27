package info.navnoire.recipeapp_client.data.source

import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.networking.api.CategoryApi
import info.navnoire.recipeapp_client.networking.response.CategoryData

class CategoryListSource(
    private val categoryApi: CategoryApi
) : SafeApiCall{

    suspend fun getChildCategories(parentId: Int): Result<List<CategoryModel>> =
        safeApiCall {
            val data = categoryApi.fetchChildCategories(parentId = parentId)
            convertToModel(data, parentId)
        }

    private fun convertToModel(data: List<CategoryData>, parentId : Int): List<CategoryModel> {
        return data.map { category ->
            CategoryModel(
                id = category.id,
                parent = parentId,
                title = category.title,
                hasChild = category.hasChild
            )
        }
    }
}

