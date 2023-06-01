package com.example.imgcreater.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imgcreater.R
import com.example.imgcreater.model.ImageEntity
import com.squareup.picasso.Picasso

class HistoryAdapter(var dataList: MutableList<ImageEntity>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.historyImage)
        val word: TextView = view.findViewById(R.id.historyWord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageData = dataList[position]

        Picasso.get().load(imageData.ImageUrl).resize(150, 150).into(holder.image)
        holder.word.text = imageData.word
    }

    override fun getItemCount(): Int = dataList.size

}