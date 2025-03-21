package com.goncalo.nutriumchallenge.common

fun Long?.isTimestampOutOfRange(timeInterval: Int): Boolean {
    return this?.let { timestamp ->
        val timePassedMillis = System.currentTimeMillis() - timestamp
        timePassedMillis >= (timeInterval * 60 * 1000)
    } ?: true
}