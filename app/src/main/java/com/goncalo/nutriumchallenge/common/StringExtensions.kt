package com.goncalo.nutriumchallenge.common

fun List<String>.getWordListAsString(numWords: Int): String {
    var wordsString = ""
    this.forEachIndexed { index, string ->

        if(index >= numWords) {
            return@forEachIndexed
        }

        wordsString += "$string "
    }.also { return wordsString.trim().replace(" ", ", ") }
}