package com.riskycase.corereview.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.riskycase.corereview.R
import com.riskycase.corereview.data.Product
import com.riskycase.corereview.data.ProductPersistence
import com.squareup.picasso.Picasso

class MainPageProductsAdapter(
    private var products: List<Product>
) : RecyclerView.Adapter<MainPageProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPageProductsAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.main_page_product,
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        Picasso.get().load(product.image).into(holder.imageView)
        holder.titleView.text = product.title
        holder.priceView.text = product.price.toString()
        holder.productItem.setOnClickListener{
            startActivity(it.context ,Intent(it.context, ViewProductActivity::class.java).putExtra("product", product), null)
        }
        holder.addToCardButton.setOnClickListener {
            ProductPersistence(it.context).addProduct(product)
        }
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.product_title)
        val priceView: TextView = view.findViewById(R.id.product_price)
        val imageView: ImageView = view.findViewById(R.id.product_image)
        val productItem: LinearLayout = view.findViewById(R.id.main_product)
        val addToCardButton : Button = view.findViewById(R.id.product_add_to_cart)
    }
}