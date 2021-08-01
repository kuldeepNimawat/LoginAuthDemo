package com.kuldeep.loginauthdemo.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {
    private val applicationContext=context.applicationContext
    private val dataStore: DataStore<Preferences>
    init {
        dataStore=applicationContext.createDataStore(
            name="my_data_store "
        )
    }

    val authToken: Flow<String?>
    get() = dataStore.data.map { preferences ->
        preferences[auth_key]
    }

    suspend fun saveAuthToken(authToken: String){
          dataStore.edit { preferences ->
           preferences[auth_key]=authToken
          }
    }

    suspend fun clearToken(){
        dataStore.edit { preferences->
            preferences.clear()
        }
    }

    companion object{
        private val auth_key= preferencesKey<String>("auth_token")

    }
}