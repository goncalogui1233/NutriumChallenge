package com.goncalo.nutriumchallenge.professionals.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("professional_prefs")

class ProfessionalDataStore(
    private val context: Context
) {

    companion object {
        private val CURRENT_PAGE = stringPreferencesKey("current_page")
        private val TOTAL_PAGES = stringPreferencesKey("total_pages")
        private val LAST_FILTER = stringPreferencesKey("last_filter")
    }

    suspend fun saveProfessionalListPageMarker(currentPage: String, totalPages: String) {
        context.dataStore.edit {
            it[CURRENT_PAGE] = currentPage
            it[TOTAL_PAGES] = totalPages
        }
    }

    suspend fun getProfessionalListPageMarker(): Pair<String?, String?> {
        val prefs = context.dataStore.data.first()
        return prefs[TOTAL_PAGES] to prefs[CURRENT_PAGE]
    }

    suspend fun saveLastFilterSelected(filter: String) {
        context.dataStore.edit {
            it[LAST_FILTER] = filter
        }
    }

    suspend fun getLastFilterSelected(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[LAST_FILTER]
    }

}