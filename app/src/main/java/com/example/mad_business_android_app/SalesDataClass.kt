package com.example.mad_business_android_app

class SalesDataClass {

    var salesID :String? = null
    var cusID : String? = null
    var productName : String? = null
    var quantity : String? = null
    var total : String? = null


    constructor(salesID:String?,cusID: String?, productName: String?, quantity: String?, total: String?) {
        this.salesID = salesID
        this.cusID = cusID
        this.productName = productName
        this.quantity = quantity
        this.total = total
    }

    constructor(){

    }
}