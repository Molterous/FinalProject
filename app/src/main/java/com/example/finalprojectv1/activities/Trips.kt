package com.example.finalprojectv1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectv1.R
import com.example.finalprojectv1.databinding.ActivityTripsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_trips.*

class Trips : AppCompatActivity() {

    private lateinit var binding : ActivityTripsBinding
    private lateinit var database : DatabaseReference

    private lateinit var userRecyclerview: RecyclerView
    private lateinit var firebaseAuth: FirebaseAuth
    var user_trip_list=arrayListOf<FetchTrips>()

    //user_trip_list.add("A","B","C","D",01)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)
        binding = ActivityTripsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        userRecyclerview = findViewById(R.id.user_Trips)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        refresh_List()


       // userArrayList = arrayListOf<FetchTrips>()
        binding.submitBtn.setOnClickListener {
            val source = binding.sourceEt.text.toString()
            val destination = binding.destinationEt.text.toString()
            val date = binding.dateEt.text.toString()
            val time = binding.timeEt.text.toString()
            val phone = (firebaseAuth.currentUser?.phoneNumber).toString()

            val form = Form(source,destination,date,time,phone)


           // yaha se kra h mene
            val user_list_form = FetchTrips(source,destination,date,time,phone)

            user_trip_list.add(user_list_form)
                Log.d("USerList",user_trip_list.size.toString())
            for (i in user_trip_list.indices) {
                Log.d("UserList", (user_trip_list[i]).toString())
            }

            //yaha tak changes kre h
            database = FirebaseDatabase.getInstance().getReference("Form")

            database.child(destination).setValue(form).addOnSuccessListener {
                binding.sourceEt.text.clear()
                binding.destinationEt.text.clear()
                binding.dateEt.text.clear()
                binding.timeEt.text.clear()

                Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show()
            }

            refresh_List()
        }

    }

    private fun refresh_List() {
        userRecyclerview.adapter = tripAdapter(this@Trips,user_trip_list)
      }
}