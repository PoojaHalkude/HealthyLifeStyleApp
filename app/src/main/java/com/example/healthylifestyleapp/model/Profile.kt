package com.example.healthylifestyleapp.model

data class User(
    val uid: String,
    val username: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var physique: Physique? = null,
    var goals: Goals? = null
)

data class Physique(
    val height: Height,
    val weight: Weight,
    val age: Int,
    val level: String,
    val gender: String
)

data class Height(var foot: Int, var inches: Int)
data class Weight(var unit: String? = "kg", var weight: Int)

data class Goals(val water: Water?, val food: Int, val sleep: Int)

data class Water(val unit: String? = "litres", val count: Int)