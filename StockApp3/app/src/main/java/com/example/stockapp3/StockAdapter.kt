package com.example.stockapp3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StockAdapter(private val mList: MutableList<Stock>) : RecyclerView.Adapter<StockAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]
        // sets the text to the textview from our itemHolder class
        holder.textView.text = item.name;
        holder.priceView.text = item.value.toString();


        holder.deleteButton.setOnClickListener{
            mList.removeAt(position)
            notifyDataSetChanged()
        }


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.nameView)
        val priceView: TextView = itemView.findViewById(R.id.priceView)
        val deleteButton : Button = itemView.findViewById(R.id.deleteButton)
    }
}