package com.oneclick.blackandoneparent.core.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.core.constants.AppConstants
import kotlinx.coroutines.flow.first

/**
 * Description: This class is used for the purpose of accessing shared preference.
 *
 * @author Ankit Mishra
 * @since 01/12/21
 *
 * @param prefDataStore Provide Preferences Data Store Object.
 */
class AppPreference(
    private val prefDataStore: DataStore<Preferences>
) {

    /**
     * This method stores the boolean value to given key.
     *
     * @param key Provide key
     * @param value Provide value which should be stored on the key.
     */
    suspend fun writeBoolean(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        prefDataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    /**
     * This method gets the boolean value for given key.
     *
     * @param key Provide key
     * @return Boolean value for the given key and if it is not there then default value.
     */
    suspend fun readBoolean(key: String,defaultValue: Boolean=false): Boolean {
        val dataStoreKey = booleanPreferencesKey(key)
        val preference = prefDataStore.data.first()
        return preference[dataStoreKey] ?: defaultValue
    }

    /**
     * This method stores the string value to given key.
     *
     * @param key Provide key
     * @param value Provide value which should be stored on the key.
     */
    suspend fun writeString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        prefDataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    /**
     * This method gets the string value for given key.
     *
     * @param key Provide key
     * @return String value for the given key and if it is not there then default value.
     */
    suspend fun readString(key: String,defaultValue: String=String()): String {
        val dataStoreKey = stringPreferencesKey(key)
        val preference = prefDataStore.data.first()
        return preference[dataStoreKey]?:defaultValue
    }


    /**
     * This method stores the int value to given key.
     *
     * @param key Provide key
     * @param value Provide value which should be stored on the key.
     */
    suspend fun writeInt(key: String, value:Int) {
        val dataStoreKey = intPreferencesKey(key)
        prefDataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    /**
     * This method gets the int value for given key.
     *
     * @param key Provide key
     * @return Int value for the given key and if it is not there then returns default value.
     */
    suspend fun readInt(key: String,defaultValue:Int=-1): Int {
        val dataStoreKey = intPreferencesKey(key)
        val preference = prefDataStore.data.first()
        return preference[dataStoreKey]?:defaultValue
    }



    /**
     * This method stores the long value to given key.
     *
     * @param key Provide key
     * @param value Provide value which should be stored on the key.
     */
    suspend fun writeLong(key: String, value:Long) {
        val dataStoreKey = longPreferencesKey(key)
        prefDataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    /**
     * This method gets the long value for given key.
     *
     * @param key Provide key
     * @return Long value for the given key and if it is not there then returns default value.
     */
    suspend fun readLong(key: String,defaultValue:Long=-1L): Long {
        val dataStoreKey = longPreferencesKey(key)
        val preference = prefDataStore.data.first()
        return preference[dataStoreKey]?:defaultValue
    }


    /**
     * Description: This method stores the float value to given key.
     *
     * @param key Provide key
     * @param value Provide value which should be stored on the key.
     */
    suspend fun writeFloat(key: String, value:Float) {
        val dataStoreKey = floatPreferencesKey(key)
        prefDataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    /**
     * Description: This method gets the float value for given key.
     *
     * @param key Provide key
     * @return Float value for the given key and if it is not there then returns default value.
     */
    suspend fun readFloat(key: String,defaultValue:Float=-1.0f): Float {
        val dataStoreKey = floatPreferencesKey(key)
        val preference = prefDataStore.data.first()
        return preference[dataStoreKey]?:defaultValue
    }
}

suspend fun AppPreference.getAccessTokenWithoutPrefix() =readString(
        AppConstants.PREF_ACCESS_TOKEN
    )
