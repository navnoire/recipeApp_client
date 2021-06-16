package info.navnoire.recipeapp_client.data

data class RecipeShortModel(val id: Int, val title: String, val main_image_url: String)

data class RecipeFullModel(val id : Int, val title : String, val ingredients : List<IngredientModel>, val steps : List<StepModel>, val main_image_url: String)

data class IngredientModel(val name : String, val amount : String, val type : IngredientType)

data class StepModel(val id : Int, val text : String, val stepImageUrl:String?)

data class CategoryModel(val id : Int, val title : String, val hasChild : Boolean)

enum class IngredientType {
        ORDINARY, HEADER
    }
