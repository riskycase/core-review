package com.riskycase.corereview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.riskycase.corereview.ui.AccountActivity
import com.riskycase.corereview.ui.CartActivity
import com.riskycase.corereview.ui.MainPageProductsAdapter
import com.riskycase.corereview.data.Product
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var queue: RequestQueue
    private lateinit var products: List<Product>
    private var pageNumber: Int = 1

    @ExperimentalBadgeUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.tool_bar))

        products = List(0) {
            return@List Product()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.main_products)
        val loading = findViewById<ProgressBar>(R.id.main_loading)
        val productsAdapter = MainPageProductsAdapter(products)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }

        queue = Volley.newRequestQueue(this)
        queue.add(
            StringRequest(
                Request.Method.GET,
                "https://fakestoreapi.com/products?limit=20&page=${pageNumber}",
                {
                    val productsObject = JSONArray(it)
                    products = List(productsObject.length()) { index ->
                        val productObject = productsObject.getJSONObject(index)
                        return@List Product(
                            id = index,
                            title = productObject.getString("title"),
                            price = productObject.getDouble("price"),
                            description = productObject.getString("description"),
                            category = productObject.getString("category"),
                            image = productObject.getString("image")
                        )
                    }
                    productsAdapter.updateProducts(products)
                    recyclerView.visibility = RecyclerView.VISIBLE
                    loading.visibility = ProgressBar.GONE
                },
                {
                    Log.e("Error", it.toString())
                })
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_cart -> {
                startActivity(Intent(baseContext, CartActivity::class.java))
                true
            }
            R.id.action_account -> {
                startActivity(Intent(baseContext, AccountActivity::class.java))
                true
            }
         else -> super.onOptionsItemSelected(item)
        }
    }

}