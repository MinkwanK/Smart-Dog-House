package com.example.schoolproject

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class DogAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<DogHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogHolder {
        return com.example.schoolproject.DogHolder(LayoutInflater.from(context).inflate(R.layout.dogdata,parent,false))
    }

    override fun onBindViewHolder(holder: com.example.schoolproject.DogHolder, position: Int) {
        holder.dogType.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
    //데이터를 로드해 보여주기 위한 뷰홀더
    class DogHolder(view: View) : RecyclerView.ViewHolder(view){
        val dogType :TextView = itemView.findViewById(R.id.dog_data_type)
    }

