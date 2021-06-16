package info.navnoire.recipeapp_client.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.squareup.picasso.Picasso
import info.navnoire.recipeapp_client.data.RecipeShortModel
import info.navnoire.recipeapp_client.networking.RecipeService
import retrofit2.HttpException
import java.io.IOException

class RecipeListPagingSource(
    private val recipeService: RecipeService,
    private val categoryId: Int = -1,
    val searchString: String = ""
) :
    PagingSource<Int, RecipeShortModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeShortModel> {
        return try {
            val pageNumber = params.key ?: 0
            val response = when (categoryId) {
                -1 -> {
                    if (searchString.isEmpty()) recipeService.fetchAllRecipeList(pageNumber = pageNumber) else
                        recipeService.fetchRecipesByName(
                            pageNumber = pageNumber,
                            searchString = searchString
                        )
                }
                else -> recipeService.fetchRecipesByCategory(
                    pageNumber = pageNumber,
                    categoryId = categoryId
                )
            }

            val recipeList = response.body()?.recipes?.map {
                RecipeShortModel(it.id, it.title, generateImageUrl(it.id))
            } ?: listOf()
            recipeList.forEach { warmImageCache(it.main_image_url) }
            val nextKey = if (response.body()?.hasNext == true) pageNumber.plus(1) else null
            val prevKey = if (response.body()?.hasPrevious == true) pageNumber.minus(1) else null
            LoadResult.Page(
                data = recipeList,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecipeShortModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    private fun generateImageUrl(recipeId: Int): String {
        return "https://navnoire.info/RecipeApp/api/image/$recipeId"
    }

    private fun warmImageCache(imageUrl : String) {
        Picasso.get().load(imageUrl).fetch()
    }

}