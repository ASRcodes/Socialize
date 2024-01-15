package com.example.socialize20.Models

class Reels{
    var reelurl:String = ""
    var reelCaption = ""
    var profileLink:String?=null

    //    for firebase
    constructor()
    constructor(reelurl: String, reelCaption: String) {
        this.reelurl = reelurl
        this.reelCaption = reelCaption
    }

    constructor(reelurl: String, reelCaption: String, profileLink: String?) {
        this.reelurl = reelurl
        this.reelCaption = reelCaption
        this.profileLink = profileLink
    }
}