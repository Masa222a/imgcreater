package com.example.imgcreater.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imgcreater.R
import com.example.imgcreater.model.generateData
import com.squareup.picasso.Picasso

class HistoryAdapter(private val dataList: MutableList<generateData>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.created_view)
        val word: TextView = view.findViewById(R.id.search_word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val generateData = dataList[position]

        Picasso.get().load(generateData.ImageUrl).into(holder.image)
        holder.word.text = generateData.word
    }

    override fun getItemCount(): Int = dataList.size

}