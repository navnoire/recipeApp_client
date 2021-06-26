package info.navnoire.recipeapp_client.networking.response

data class StatusResponse(
    val message: String,
    val statusCode: Int,
    val timestamp: Long
)