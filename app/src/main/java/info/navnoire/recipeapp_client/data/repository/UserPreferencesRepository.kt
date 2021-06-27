package info.navnoire.recipeapp_client.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "UserPreferencesRepo"

private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext context : Context) {
    private val dataStore = context.dataStore

    val accessTokenFlow = dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ACCESS_TOKEN]
        }

    val refreshTokenFlow = dataStore.data.map { preferences ->
            preferences[PreferencesKeys.REFRESH_TOKEN]
        }

    suspend fun updateAccessToken(token : String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACCESS_TOKEN] = token
        }
    }

    suspend fun updateRefreshToken(token : String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.REFRESH_TOKEN] = token
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private object PreferencesKeys{
        val ACCESS_TOKEN = stringPreferencesKey("key_access_token")
        val REFRESH_TOKEN = stringPreferencesKey("key_refresh_token")
        val REFRESH_EXPIRES_AT = longPreferencesKey("key_refresh_expdate")
        val ACCESS_EXPIRES_AT = longPreferencesKey("key_access_expdate")
    }
}

