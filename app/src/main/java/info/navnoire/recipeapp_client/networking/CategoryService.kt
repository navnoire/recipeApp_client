package info.navnoire.recipeapp_client.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryService {
    @GET(value = "category")
    suspend fun fetchAllCategories(): Response<List<CategoryData>>

    @GET(value = "category/child/{id}")
    suspend fun fetchChildCategories(
        @Path(value = "id") parentId: Int
    ): Response<List<CategoryData>>
}