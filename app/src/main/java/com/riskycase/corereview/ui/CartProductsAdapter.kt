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

class CartProductsAdapter(
    private var products: List<Product>
) : RecyclerView.Adapter<CartProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartProductsAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cart_product,
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        Picasso.get().load(product.image).into(holder.imageView)
        holder.titleView.text = product.title
        holder.priceView.text = product.price.toString()
    }

    override fun getItemCount(): Int = products.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.product_title)
        val priceView: TextView = view.findViewById(R.id.product_price)
        val imageView: ImageView = view.findViewById(R.id.product_image)
    }
}