package com.example.mad_business_android_app

class SalesDataClass {

    var cusID : String? = null
    var productName : String? = null
    var quentity : String? = null
    var total : String? = null

    constructor(cusID: String?, productName: String?, quentity: String?, total: String?) {
        this.cusID = cusID
        this.productName = productName
        this.quentity = quentity
        this.total = total
    }
}