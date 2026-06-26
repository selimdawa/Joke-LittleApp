package com.littleapp.joke.Model

data class Joke(
    var category: String? = null,
    var type: String? = null,
    var joke: String? = null,
    var setup: String? = null,
    var delivery: String? = null
)