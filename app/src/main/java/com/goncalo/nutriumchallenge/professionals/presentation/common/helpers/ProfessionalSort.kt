package com.goncalo.nutriumchallenge.professionals.presentation.common.helpers

enum class ProfessionalSort(val typeName: String) {
    BEST_MATCH("best_match"),
    RATING("rating"),
    MOST_POPULAR("most_popular") ;

    companion object  {
        fun getEnumByTypeName(typeName: String): ProfessionalSort {
            return when (typeName) {
                RATING.typeName -> RATING
                MOST_POPULAR.typeName -> MOST_POPULAR
                else -> BEST_MATCH
            }
        }
    }
}