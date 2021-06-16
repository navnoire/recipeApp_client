package info.navnoire.recipeapp_client.data.source.remote

import info.navnoire.recipeapp_client.data.IngredientModel
import info.navnoire.recipeapp_client.data.IngredientType
import info.navnoire.recipeapp_client.data.RecipeFullModel
import info.navnoire.recipeapp_client.data.StepModel
import info.navnoire.recipeapp_client.networking.RecipeService
import retrofit2.HttpException
import info.navnoire.recipeapp_client.data.Result
import java.io.IOException

class SingleRecipeSource(private val recipeService: RecipeService) {
    companion object {
        const val IMAGE_STORAGE_BASE = "https://navnoire.info/RecipeApp/api/image/"
    }

    suspend fun load(recipeId: Int): Result<RecipeFullModel> {
        return try {
            val response = recipeService.fetchSingleRecipe(recipeId)
            val result = response.body()?.let {
                RecipeFullModel(it.id, it.title,
                    it.ingredients.map { data ->
                        IngredientModel(
                            data.name,
                            data.amount,
                            when(data.type) {
                                0 -> IngredientType.ORDINARY
                                else -> IngredientType.HEADER
                            }
                        )
                    },
                    it.steps.map { step ->
                        StepModel(
                            step.id,
                            step.text,
                            generateStepImageUrl(stepId = step.id, hasImage = step.imageExists)
                        )
                    },
                    "$IMAGE_STORAGE_BASE${it.id}"
                )
            }?: RecipeFullModel(0,"", listOf(), listOf(),"")
            Result.Success(result)
        } catch (e:Exception) {
            Result.Error(e)
        }
    }

    private fun generateStepImageUrl(hasImage: Boolean, stepId: Int): String? {
        return if (hasImage) "${IMAGE_STORAGE_BASE}step/$stepId" else null
    }

}