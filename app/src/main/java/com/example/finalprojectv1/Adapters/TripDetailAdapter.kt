package com.example.finalprojectv1.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectv1.R
import com.example.finalprojectv1.activities.ChatMessage
import com.example.finalprojectv1.utils.ChatTripDetail
import com.example.finalprojectv1.utils.UserNameLocation

class TripDetailAdapter(private val context: Context, private val DetList : ArrayList<UserNameLocation>)
    : RecyclerView.Adapter<TripDetailAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.chat_name,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = DetList[position]

//        holder.sourceC.text = currentitem.source
//        holder.destinationC.text = currentitem.destination
//        holder.dateC.text = currentitem.date
//        holder.timeC.text = currentitem.time
        holder.name.text = currentItem.name

        holder.itemView.setOnClickListener {

            var intent = Intent( context, ChatMessage::class.java )
            intent.putExtra( "Number", currentItem.name )
            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return DetList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

//        val sourceC : TextView = itemView.findViewById(R.id.tvSourceC)
//        val destinationC : TextView = itemView.findViewById(R.id.tvDestinationC)
//        val dateC : TextView = itemView.findViewById(R.id.tvdateC)
//        val timeC : TextView = itemView.findViewById(R.id.tvtimeC)
          val name : TextView = itemView.findViewById(R.id.tv_chatNames)

    }


}