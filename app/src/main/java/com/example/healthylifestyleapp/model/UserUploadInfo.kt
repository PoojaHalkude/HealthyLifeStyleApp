package com.example.healthylifestyleapp.model

data class UserUploadInfo(
    var userName: String,
    var imageURL: String,
    var myemail: String,
    var mobile: String
) {
    constructor() : this("", "", "", "")
}
