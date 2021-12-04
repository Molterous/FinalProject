package com.example.finalprojectv1.activities

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectv1.ProfileActivity
import com.example.finalprojectv1.R

class tripAdapter(private val context: Context, private val TripList : ArrayList<FetchTrips>) : RecyclerView.Adapter<tripAdapter.MyViewHolder>() {

//    private lateinit var mListner:onItemClickListner
//    interface onItemClickListner{
//        fun onItemClick(position:Int, item: String?)
//    }
//
//    fun setOnItemClickListner(listner: onItemClickListner){
//        mListner = listner
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.trip_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = TripList[position]

        holder.source.text = currentitem.source
        holder.destination.text = currentitem.destination
        holder.date.text = currentitem.date
        holder.time.text = currentitem.time

        holder.destination.setOnClickListener {

          //  val isprofile = true
            val phone = currentitem.userid
            Log.d("Acitivity",phone.toString())
            Toast.makeText( context,  phone,Toast.LENGTH_LONG).show()

            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("ExtraPhone",phone)
            //intent.putExtra("ExtraProfile",phone)
            context.startActivity(intent)

//                Intent(context,ProfileActivity::class.java).also{
//                    it.putExtra("ExtraPhone",isprofile)
//                    it.putExtra("ExtraProfile",phone)
//                    startActivity(it)
//
//            }
            //      val =true
//
        }

    }

    override fun getItemCount(): Int {
        return TripList.size
    }


    class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val source : TextView = itemView.findViewById(R.id.tvSource)
        val destination : TextView = itemView.findViewById(R.id.tvDestination)
        val date : TextView = itemView.findViewById(R.id.tvdate)
        val time : TextView = itemView.findViewById(R.id.tvtime)




//        init{
//            itemView.setOnClickListener {
//                val currentitem = TripList[adapterPosition]
//
//                listner.onItemClick(adapterPosition,currentitem.phone)
//            }
 //       }

    }

}