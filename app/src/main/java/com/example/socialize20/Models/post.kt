package com.example.socialize20.Models
//This class is made for save the posted img and upload over Firebase
class Post {
    var posturl:String = ""
    var postCaption = ""
//    Here we'll create two variable to fetch user's name over his/her post and the time i.e 5 min ago,etc
    var uid:String = ""
    var time:String = ""

//    for firebase
    constructor()
    constructor(posturl: String, postCaption: String) {
        this.posturl = posturl
        this.postCaption = postCaption
    }

    constructor(posturl: String, postCaption: String, uid: String, time: String) {
        this.posturl = posturl
        this.postCaption = postCaption
        this.uid = uid
        this.time = time
    }

}