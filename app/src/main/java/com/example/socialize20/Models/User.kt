package com.example.socialize20.Models

class User {
    var email: String? = null
    var image : String? = null
    var name : String? = null
    var password : String? = null

//    this blank constructor is for firebase
    constructor()
    constructor(email: String?, image: String?, name: String?, password: String?) {
        this.email = email
        this.image = image
        this.name = name
        this.password = password
    }
    constructor(name: String?, email: String?, password: String?) {
        this.name = name
        this.email = email
        this.password = password
    }
//Constructor for Signup activity
    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }
}