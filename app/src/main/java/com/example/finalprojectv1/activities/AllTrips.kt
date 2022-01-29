

package com.example.finalprojectv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectv1.ProfileActivity
import com.example.finalprojectv1.R
import com.example.finalprojectv1.databinding.ActivityAllTripsBinding
import com.example.finalprojectv1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.trip_item.*

class AllTrips : AppCompatActivity() {

    private lateinit var binding: ActivityAllTripsBinding

    private lateinit var database : DatabaseReference
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userArrayList: ArrayList<FetchTrips>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllTripsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navbar
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_Add_Trip-> startActivity(Intent( this, Trips::class.java ))
                R.id.ic_profile->startActivity(Intent( this, ProfileActivity::class.java ))
//                R.id.ic_All_Trip-> startActivity(Intent(this,AllTrips::class.java))
                R.id.ic_chat->startActivity(Intent( this, ChatList::class.java ))
            }
            true
        }
        //navbar end

        userRecyclerview = findViewById(R.id.recyclerTrips)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<FetchTrips>()
        getUserData()

    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Form")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val user = userSnapshot.getValue(FetchTrips::class.java)
                        userArrayList.add(user!!)

                    }

                    userRecyclerview.adapter = tripAdapter(this@AllTrips,userArrayList)


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}


