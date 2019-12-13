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
    val height: Int,
    val weight: Int,
    val age: Int,
    val level: String,
    val gender: String
) {
    constructor() : this(0, 0, 0, "Beginner", "Male")
}

data class Height(var foot: Int, var inches: Int)
data class Weight(var unit: String? = "kg", var weight: Int)

data class Goals(val water: Water?, val food: Int, val sleep: Int) {
    constructor() : this(Water("litres", 0), 0, 0)
}

data class Water(val unit: String? = "litres", val count: Int) {
    constructor() : this("liters", 0)
}

enum class Type {
    WATER, FOOD, SLEEP
}

data class Activity(
    var name: String = "",
    var quantity: Int = 0,
    var type: String,
    var lastModifiedTimestamp: String
) {
    constructor() : this("", 0, "Water", "")
}

data class Preferences(
    var waterReminder: Boolean = false,
    var sleepReminder: Boolean = false,
    var foodReminder: Boolean = false
) {
}