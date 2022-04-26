package com.example.finalprojectv1.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectv1.R
import com.example.finalprojectv1.activities.ChatMessage
import com.example.finalprojectv1.utils.ChatTripDetail
import com.example.finalprojectv1.utils.UserNameLocation

class TripDetailAdapter(private val context: Context, private val DetList : ArrayList<UserNameLocation>)
    : RecyclerView.Adapter<TripDetailAdapter.MyViewHolder>() {

    private lateinit var userArrayList: ArrayList<UserNameLocation>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        userArrayList = arrayListOf<UserNameLocation>()
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

        holder.name.setOnClickListener {

            var intent = Intent( context, ChatMessage::class.java )
            intent.putExtra( "Number", currentItem.name )
            context.startActivity(intent)

        }

        holder.check_box_chat.setOnClickListener {

            if(holder.check_box_chat.isChecked){
                userArrayList.add(currentItem)
            }
            else if(userArrayList.contains(currentItem)){
                userArrayList.remove(currentItem)
            }
            Toast.makeText( context,userArrayList.size.toString(), Toast.LENGTH_SHORT).show()
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
        val check_box_chat: CheckBox = itemView.findViewById(R.id.check_box_chat)


    }


}