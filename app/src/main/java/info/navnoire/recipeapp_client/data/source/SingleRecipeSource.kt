package info.navnoire.recipeapp_client.data.source

import info.navnoire.recipeapp_client.data.IngredientModel
import info.navnoire.recipeapp_client.data.IngredientType
import info.navnoire.recipeapp_client.data.RecipeFullModel
import info.navnoire.recipeapp_client.data.StepModel
import info.navnoire.recipeapp_client.networking.api.RecipeApi
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.networking.response.RecipeFullData
import retrofit2.Callback

class SingleRecipeSource(private val recipeApi: RecipeApi) : SafeApiCall{
    companion object {
        const val IMAGE_STORAGE_BASE = "https://navnoire.info/RecipeApp/api/image/"
    }

    suspend fun load(recipeId: Int): Result<RecipeFullModel> = safeApiCall {
        val data = recipeApi.fetchSingleRecipe(recipeId = recipeId)
        convertToModel(data)
    }

    private fun generateStepImageUrl(hasImage: Boolean, stepId: Int): String? {
        return if (hasImage) "${IMAGE_STORAGE_BASE}step/$stepId" else null
    }

    private fun convertToModel(data:RecipeFullData) : RecipeFullModel{
        return RecipeFullModel(data.id, data.title,
            data.ingredients.map { ingredient ->
                IngredientModel(
                    ingredient.name,
                    ingredient.amount,
                    when(ingredient.type) {
                        0 -> IngredientType.ORDINARY
                        else -> IngredientType.HEADER
                    }
                )
            },
            data.steps.map { step ->
                StepModel(
                    step.id,
                    step.text,
                    generateStepImageUrl(stepId = step.id, hasImage = step.imageExists)
                )
            },
            "$IMAGE_STORAGE_BASE${data.id}"
        )
    }

}