package info.navnoire.recipeapp_client.networking.response

data class TokenResponse(
    val refreshToken: String,
    val accessToken: String,
    val type: String
)