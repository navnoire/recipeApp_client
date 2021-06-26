package info.navnoire.recipeapp_client.networking.request

data class SignupRequest(val username: String, val email : String, val password : String)

data class SigninRequest(val username: String, val password: String)

data class RefreshTokenRequest(val refreshToken : String)