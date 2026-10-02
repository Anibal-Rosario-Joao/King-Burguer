package com.anibal.kingburguer.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val USER_CREDENTIALS_NAME = "user_credentials"
private val Context.dataStore by preferencesDataStore(name = USER_CREDENTIALS_NAME)

class KingBurguerLocalStorage(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    val userCredetialsFlow = dataStore.data.map { preferences ->
        mapUserCredentials(preferences)
    }

    suspend fun fetchInitialUserCredential(): UserCredencials{
        val perf = dataStore.data.first().toPreferences()
        val userCredencials = mapUserCredentials(perf)
        Log.d("KingBurguerLocalStorage", "usuario encontrado: $userCredencials")
        return userCredencials
    }

    suspend fun updateUserCredential(userCredencials: UserCredencials){
        dataStore.edit { preferences ->
            preferences[EXPIRERES_TIMESTAMP] = System.currentTimeMillis() + (userCredencials.expiresTimestamp * 1000L)
            preferences[ACCESS_TOKEN] = userCredencials.accessToken
            preferences[REFRESH_TOKEN] = userCredencials.refreshToken
            preferences[TOKEN_TYPE] = userCredencials.tokenTypes
        }
    }

    private fun mapUserCredentials(preferences: Preferences): UserCredencials{
        val expires = preferences[EXPIRERES_TIMESTAMP]?: 0
        val accessToken = preferences[ACCESS_TOKEN]?: ""
        val refreshToken = preferences[REFRESH_TOKEN]?: ""
        val tokenType = preferences[TOKEN_TYPE]?: ""

        return UserCredencials(accessToken,refreshToken,expires,tokenType)
    }

    companion object{
        //escrita/leitura
        val EXPIRERES_TIMESTAMP = longPreferencesKey("expires_timestamp")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val TOKEN_TYPE = stringPreferencesKey("token_type")
    }
}