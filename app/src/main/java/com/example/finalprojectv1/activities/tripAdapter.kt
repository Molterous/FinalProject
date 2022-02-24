package com.example.finalprojectv1.activities

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectv1.LoginActivity
import com.example.finalprojectv1.ProfileActivity
import com.example.finalprojectv1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.profile.view.*
import kotlinx.android.synthetic.main.trip_item.view.*
import java.io.File

class tripAdapter(private val context: Context, private val TripList : ArrayList<FetchTrips>) : RecyclerView.Adapter<tripAdapter.MyViewHolder>() {

    private  lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.trip_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = TripList[position]


        holder.source.text = currentitem.source
        holder.ph.text = currentitem.phone
        holder.destination.text = currentitem.destination
        holder.date.text = currentitem.date
        holder.time.text = currentitem.time
        holder.car.text = currentitem.car
        holder.seat.text = currentitem.seat
        holder.rating.text = "0"



        val imageID = currentitem.phone.toString()

        storageReference = FirebaseStorage.getInstance().getReference("image/$imageID.png")


        val localfile= File.createTempFile("temp",".png")
        storageReference.getFile(localfile)

            .addOnSuccessListener {
                val Bitmap = BitmapFactory.decodeFile(localfile.absolutePath)

                holder.p_image.setImageBitmap(Bitmap)
                Toast.makeText(context, "ho gyi image load", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(context, "image load nhi hui ", Toast.LENGTH_SHORT)
                    .show()

            }





        holder.destination.setOnClickListener {

          //  val isprofile = true
            val phone = currentitem.phone
            Log.d("Acitivity",phone.toString())
            Toast.makeText( context,  phone,Toast.LENGTH_LONG).show()

            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("ExtraPhone",phone)
            //intent.putExtra("ExtraProfile",phone)
            context.startActivity(intent)

            holder.myButton.setOnClickListener {

                var database = FirebaseDatabase.getInstance().getReference("List")


                val ph = holder.ph.text.toString()
                val dest = holder.destination.text.toString()
                val sour = holder.source.text.toString()
                val time = holder.time.text.toString()
                val date = holder.date.text.toString()



                val firebaseAuth = FirebaseAuth.getInstance()
                var phone = (firebaseAuth.currentUser?.phoneNumber).toString()

                if( !ph.equals(phone, true) ) {

                    database.child(ph).child( dest+"_"+sour+"_"+date+"_"+time ).child(phone).setValue( "N/A" ).addOnSuccessListener {
                        Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show()
                    }

                    database.child(phone).child( dest+"_"+sour+"_"+date+"_"+time ).child(ph).setValue( "N/A" ).addOnSuccessListener {
                        Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText( context,"Yours own trip, Booking not possible !!"
                                            ,Toast.LENGTH_LONG).show()
                }

                val context = context
                val intent = Intent( context, ChatList::class.java)
                context.startActivity(intent)

            }

        }
        holder.c_image.setOnClickListener {
//        val dest = holder.destination.text.toString()
            val sour = holder.source.toString()

            val source = currentitem.source
            val destination= currentitem.destination
            val intent = Intent(context, FetchImages::class.java)
            intent.putExtra("source",source)
            intent.putExtra("destination",destination)
            context.startActivity(intent)

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
        val ph : TextView = itemView.findViewById(R.id.tv_phone_number)
        val car : TextView = itemView.findViewById(R.id.tvCar)
        val seat : TextView = itemView.findViewById(R.id.tvSeat)
        val rating : TextView = itemView.findViewById(R.id.tvRating)
        val myButton = itemView.findViewById<Button>(R.id.BookBtnAllTrip)
        val p_image= itemView.profile_image_trips
        val c_image=itemView.CarImage

    }

}