package com.goncalo.nutriumchallenge.professionals.presentation.common.helpers

enum class ProfessionalSortType(val typeName: String) {
    BEST_MATCH("best_match"),
    RATING("rating"),
    MOST_POPULAR("most_popular") ;

    companion object  {
        fun getEnumByTypeName(typeName: String): ProfessionalSortType {
            return when (typeName) {
                RATING.typeName -> RATING
                MOST_POPULAR.typeName -> MOST_POPULAR
                else -> BEST_MATCH
            }
        }
    }
}