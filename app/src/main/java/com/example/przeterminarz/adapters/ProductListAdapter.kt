package com.example.przeterminarz.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.przeterminarz.databinding.ItemProductBinding
import com.example.przeterminarz.model.Product

class ProductItem(private val itemViewBinding: ItemProductBinding) :
    RecyclerView.ViewHolder(itemViewBinding.root) {
    fun onBind(productItem: Product, onItemClick: () -> Unit) = with(itemViewBinding) {
        productName.text = productItem.name
        expirationDate.text = productItem.expirationDate.toString()
        category.text = productItem.category
        if (productItem.quantity != null)
            quantity.text = productItem.quantity.toString()
        else
            quantity.visibility = View.GONE
        root.setOnClickListener {
            onItemClick()
        }
    }

    fun onHold(productItem: Product, onLongItemClick: () -> Unit) = with(itemViewBinding) {
        productName.text = productItem.name
        expirationDate.text = productItem.expirationDate.toString()
        category.text = productItem.category
        if (productItem.quantity != null)
            quantity.text = productItem.quantity.toString()
        else
            quantity.visibility = View.GONE
        root.setOnLongClickListener {
            onLongItemClick()
            true
        }
    }
}

class ProductListAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onLongItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ProductItem>() {
    var productList: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater)

        return ProductItem(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductItem, position: Int) {
        holder.onBind(productList[position]) { onItemClick(position) }
        holder.onHold(productList[position]) { onLongItemClick(position) }
    }
}