package com.anonymous.networkingwithretrofit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.networkingwithretrofit.R
import com.anonymous.networkingwithretrofit.models.Product
import com.bumptech.glide.Glide

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var price: TextView

    private val differCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_product_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = differ.currentList[position]

        image = holder.itemView.findViewById(R.id.image)
        title = holder.itemView.findViewById(R.id.title)
        description = holder.itemView.findViewById(R.id.description)
        price = holder.itemView.findViewById(R.id.price)

        holder.itemView.apply {
            Glide.with(this).load(product.image).into(image)
            title.text = product.title
            description.text = product.description
            price.text = "$${product.price}"
        }
    }

}