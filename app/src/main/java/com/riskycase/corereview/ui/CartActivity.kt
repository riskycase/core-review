package com.riskycase.corereview.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.riskycase.corereview.R
import com.riskycase.corereview.data.ProductPersistence

class CartActivity : AppCompatActivity() {

    private lateinit var productPersistence: ProductPersistence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        productPersistence = ProductPersistence(applicationContext)
        Toast.makeText(applicationContext, productPersistence.getAllProducts()[0].title, Toast.LENGTH_SHORT).show()
    }
}