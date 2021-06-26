package info.navnoire.recipeapp_client.networking.api

import info.navnoire.recipeapp_client.networking.response.RecipeFullData
import info.navnoire.recipeapp_client.networking.response.RecipePageResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    @GET(value = "recipe")
    suspend fun fetchAllRecipeList(
        @Query(value = "page") pageNumber: Int
    ): Response<RecipePageResponse>

    @GET(value = "recipe/category/{id}")
    suspend fun fetchRecipesByCategory(
        @Path("id") categoryId: Int,
        @Query(value = "page") pageNumber: Int
    ): Response<RecipePageResponse>

    @GET(value = "recipe/{id}")
    suspend fun fetchSingleRecipe(
        @Path("id") recipeId : Int
    ) : RecipeFullData

    @GET(value = "recipe/search/{title}")
    suspend fun fetchRecipesByName(
        @Path("title") searchString : String,
        @Query(value = "page") pageNumber: Int
    ) : Response<RecipePageResponse>

    @GET(value = "recipe/{id}")
    fun fetchSingleRecipe2(
        @Path("id") recipeId : Int
    ) : Call<RecipeFullData>
}