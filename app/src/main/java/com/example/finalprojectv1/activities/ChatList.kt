package com.example.finalprojectv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectv1.Adapters.TripDetailAdapter
import com.example.finalprojectv1.ProfileActivity
import com.example.finalprojectv1.R
import com.example.finalprojectv1.databinding.ActivityChatListBinding
import com.example.finalprojectv1.utils.UserNameLocation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class ChatList : AppCompatActivity() {

    private lateinit var binding : ActivityChatListBinding
    private lateinit var database : DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<UserNameLocation>

    private val TAG = "CHAT_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // navbar
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_Add_Trip-> startActivity(Intent( this, Trips::class.java ))
                R.id.ic_profile->startActivity(Intent( this, ProfileActivity::class.java ))
                R.id.ic_All_Trip-> startActivity(Intent(this,AllTrips::class.java))
//                R.id.ic_chat->startActivity(Intent( this, ChatList::class.java ))
            }
            true
        }
        //navbar end

        firebaseAuth = FirebaseAuth.getInstance()

        userRecyclerview = findViewById(R.id.recyclerChatTrips)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<UserNameLocation>()
        tripData()

    }


    private fun tripData() {

        var database = FirebaseDatabase.getInstance().getReference("List")

        database.child( (firebaseAuth.currentUser?.phoneNumber).toString() ).addValueEventListener(
            object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    userArrayList.clear()

                    for (userSnapshot in snapshot.children) {

                        var tempUser = userSnapshot.getValue().toString()
                        Log.d(TAG, "Received Data: $tempUser\n")
                        userArrayList.add( UserNameLocation( tempUser.substring(1,14), tempUser ) )

//                        user = tempUser.split("_").toTypedArray()
//                        userArrayList.add( ChatTripDetail( user[0], user[1], user[2], user[3]) )
//                        Toast.makeText(this@ChatList, tempUser, Toast.LENGTH_LONG).show()

                    }

                    userRecyclerview.adapter = TripDetailAdapter(this@ChatList,userArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}