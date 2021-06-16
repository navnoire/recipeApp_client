package info.navnoire.recipeapp_client.networking

data class RecipePageResponse(
    val recipes: List<RecipeShortData>,
    val current: Int,
    val hasPrevious: Boolean,
    val hasNext: Boolean
)

data class RecipeShortData(val id: Int, val title: String)

data class RecipeFullData(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientData>,
    val steps: List<StepData>
)

data class IngredientData(val name: String, val amount: String, val type: Int)

data class StepData(val id: Int, val text: String, val imageExists: Boolean)

data class CategoryData(val id: Int, val title: String, val hasChild: Boolean)