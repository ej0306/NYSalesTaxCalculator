package com.example.salestaxcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Data class to hold item name and price
data class Item(val name: String, val price: Double)

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemNameTextView: TextView = view.findViewById(R.id.itemNameTextView)
        val itemPriceTextView: TextView = view.findViewById(R.id.itemPriceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemNameTextView.text = item.name
        holder.itemPriceTextView.text = "$%.2f".format(item.price)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
