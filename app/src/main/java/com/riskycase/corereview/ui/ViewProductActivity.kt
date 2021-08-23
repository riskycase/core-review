package com.riskycase.corereview.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.riskycase.corereview.R
import com.riskycase.corereview.data.Product
import com.riskycase.corereview.data.ProductPersistence
import com.squareup.picasso.Picasso

class ViewProductActivity : AppCompatActivity() {

    private lateinit var productPersistence: ProductPersistence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_product)
        setSupportActionBar(findViewById(R.id.tool_bar))

        productPersistence = ProductPersistence(applicationContext)

        val product = intent.getSerializableExtra("product") as Product
        Picasso.get().load(product.image).into(findViewById<ImageView>(R.id.product_image))
        findViewById<TextView>(R.id.product_title).text = product.title
        title = product.title
        findViewById<TextView>(R.id.product_price).text = product.price.toString()
        findViewById<TextView>(R.id.product_description).text = product.description
        findViewById<Button>(R.id.product_add_to_cart).setOnClickListener {
            productPersistence.addProduct(product)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_view, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_cart -> {
                startActivity(Intent(baseContext, CartActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}