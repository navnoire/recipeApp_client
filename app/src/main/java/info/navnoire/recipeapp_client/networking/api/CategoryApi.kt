package info.navnoire.recipeapp_client.networking.api

import info.navnoire.recipeapp_client.networking.response.CategoryData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CategoryApi {
    @GET(value = "category")
    suspend fun fetchAllCategories(): List<CategoryData>

    @GET(value = "category/child/{id}")
    suspend fun fetchChildCategories(
        @Path(value = "id") parentId: Int,
        @Header(value = "Authorization") token : String
    ): List<CategoryData>
}