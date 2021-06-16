package info.navnoire.recipeapp_client.data.source.remote

import info.navnoire.recipeapp_client.data.CategoryModel
import info.navnoire.recipeapp_client.data.Result
import info.navnoire.recipeapp_client.networking.CategoryService

class CategoryListSource(
    private val categoryService: CategoryService
) {

    suspend fun getChildCategories(parentId: Int): Result<List<CategoryModel>> {
        return try {
            val response = categoryService.fetchChildCategories(parentId)
            val result = response.body()?.let {
                it.map { data ->
                    CategoryModel(
                        id = data.id,
                        title = data.title,
                        hasChild = data.hasChild
                    )
                }
            } ?: listOf()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    sealed class LoadResult<Item : Any> {
        data class Success<Item : Any>(val data: Item) : LoadResult<Item>()
        data class Error<Item : Any>(val throwable: Throwable) : LoadResult<Item>()
    }
}

