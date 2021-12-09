

package com.example.finalprojectv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectv1.ProfileActivity
import com.example.finalprojectv1.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class AllTrips : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<FetchTrips>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_trips)

        // navbar
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_Add_Trip-> startActivity(Intent( this, Trips::class.java ))
                R.id.ic_profile->startActivity(Intent( this, ProfileActivity::class.java ))
//                R.id.ic_All_Trip-> startActivity(Intent(this,AllTrips::class.java))
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



