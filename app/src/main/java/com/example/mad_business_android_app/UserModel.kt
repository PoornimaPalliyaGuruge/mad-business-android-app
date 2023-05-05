package com.example.mad_business_android_app

class UserModel {
    var userId : String? = null
    var name : String? = null
    var address : String? = null
    var age : Number? = null
    var number : String? = null
    var email : String? = null
    var female : Boolean? = null
    var male : Boolean? = null
    var payment: String? = null

    constructor(
        userId: String?,
        name: String?,
        address: String?,
        age: Number?,
        number: String?,
        email: String?,
        female: Boolean?,
        male: Boolean?,
        payment: String?
    ) {
        this.userId = userId
        this.name = name
        this.address = address
        this.age = age
        this.number = number
        this.email = email
        this.female = female
        this.male = male
        this.payment = payment
    }

    constructor(){

    }


}