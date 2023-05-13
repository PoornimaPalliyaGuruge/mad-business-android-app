package com.example.mad_business_android_app.models
class ExpenseData {

    var ExpName:String?=null
    var Expamount:String?=null
    var Expdate:String?=null
    var ExpCategory:String?=null

    constructor(ExpName:String?, Expamount:String?, ExpDate:String,ExpCat:String){

        this.ExpName=ExpName
        this.Expamount = Expamount
        this.Expdate= ExpDate
        this.ExpCategory=ExpCat


    }
    constructor(){

    }
}