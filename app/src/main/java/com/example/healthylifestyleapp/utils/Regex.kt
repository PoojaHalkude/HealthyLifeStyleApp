package com.example.healthylifestyleapp.utils

object Regex {
    const val PASSWORD = "^(?=.*\\d)(?=.*[A-Z])(?=.*[!@#$%&]).{8,20}\$"
    const val PHONE_NUMBER = "^[0-9]{3,13}$"
    const val POSTAL_CODE = "^[a-zA-Z0-9]{3,16}$"
    const val ALPHABETIC = "[a-zA-Z ]*"
    const val ALPHA_NUMERIC = "[a-zA-Z0-9. ]*"

}