package com.riskycase.corereview.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riskycase.corereview.R
import com.riskycase.corereview.data.Product
import com.riskycase.corereview.data.ProductPersistence

class CartActivity : AppCompatActivity() {

    private lateinit var productPersistence: ProductPersistence
    private lateinit var products: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setSupportActionBar(findViewById(R.id.tool_bar))

        productPersistence = ProductPersistence(applicationContext)

        findViewById<Button>(R.id.checkout_button).setOnClickListener {
            productPersistence.removeAll()
            Toast.makeText(applicationContext, "Purchase successful", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        products = productPersistence.getAllProducts()
        var total : Double = 0.0
        products.forEach {
            total += it.price
        }
        findViewById<TextView>(R.id.product_total).text = total.toString()
        findViewById<TextView>(R.id.product_count).text = "Total number of products: ${products.size.toString()}"

        val recyclerView = findViewById<RecyclerView>(R.id.cart_products)
        val productsAdapter = CartProductsAdapter(products)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }
    }

}