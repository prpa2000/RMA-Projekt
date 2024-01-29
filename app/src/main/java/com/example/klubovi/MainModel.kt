package com.example.klubovi

class MainModel {
    var curl: String = ""
    var name: String = ""
    var trophies: String = ""
    var year: String = ""

    constructor()

    constructor(curl: String, name: String, trophies: String,  year: String) {
        this.curl = curl
        this.name = name
        this.trophies = trophies
        this.year = year

    }


}