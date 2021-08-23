package com.riskycase.corereview.data

import java.io.Serializable

class Product() : Serializable {
    var id: Int = 0
    var title: String = ""
    var price: Double = 0.0
    var description: String = ""
    var category: String = ""
    var image: String = ""

    constructor(id: Int, title: String, price: Double, description: String, category: String, image: String) : this() {
        this.id = id
        this.title = title
        this.price = price
        this.description = description
        this.category = category
        this.image = image
    }
}
