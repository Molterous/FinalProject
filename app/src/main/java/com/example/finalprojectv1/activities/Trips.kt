
package com.example.finalprojectv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectv1.ProfileActivity
import com.example.finalprojectv1.R
import com.example.finalprojectv1.databinding.ActivityTripsBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_trips.*
import java.text.SimpleDateFormat

//class Trips : AppCompatActivity() {
//
//    private lateinit var binding : ActivityTripsBinding
//    private lateinit var database : DatabaseReference
//
//    private lateinit var userRecyclerview: RecyclerView
//    private lateinit var firebaseAuth: FirebaseAuth
//    var user_trip_list=arrayListOf<FetchTrips>()
//
//    //user_trip_list.add("A","B","C","D",01)
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_trips)
//        binding = ActivityTripsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        firebaseAuth = FirebaseAuth.getInstance()
//        userRecyclerview = findViewById(R.id.user_Trips)
//        userRecyclerview.layoutManager = LinearLayoutManager(this)
//        userRecyclerview.setHasFixedSize(true)
//        refresh_List()
//
//
//       // userArrayList = arrayListOf<FetchTrips>()
//        binding.submitBtn.setOnClickListener {
//            val source = binding.sourceEt.text.toString()
//            val destination = binding.destinationEt.text.toString()
//            val date = binding.dateEt.text.toString()
//            val time = binding.timeEt.text.toString()
//            val phone = (firebaseAuth.currentUser?.phoneNumber).toString()
//
//            val form = Form(source,destination,date,time,phone)
//
//
//           // yaha se kra h mene
//            val user_list_form = FetchTrips(source,destination,date,time,phone)
//
//            user_trip_list.add(user_list_form)
//                Log.d("USerList",user_trip_list.size.toString())
//            for (i in user_trip_list.indices) {
//                Log.d("UserList", (user_trip_list[i]).toString())
//            }
//
//            //yaha tak changes kre h
//            database = FirebaseDatabase.getInstance().getReference("Form")
//
//            database.child(destination).setValue(form).addOnSuccessListener {
//                binding.sourceEt.text.clear()
//                binding.destinationEt.text.clear()
//                binding.dateEt.text.clear()
//                binding.timeEt.text.clear()
//
//                Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener{
//                Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show()
//            }
//
//            refresh_List()
//        }
//
//    }
//
//    private fun refresh_List() {
//        userRecyclerview.adapter = tripAdapter(this@Trips,user_trip_list)
//      }
//}

class Trips : AppCompatActivity() {

    private lateinit var binding : ActivityTripsBinding
    private lateinit var database : DatabaseReference

    private lateinit var userRecyclerview: RecyclerView
    private lateinit var firebaseAuth: FirebaseAuth
    var userArrayList = arrayListOf<FetchTrips>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)
        binding = ActivityTripsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        firebaseAuth = FirebaseAuth.getInstance()
        userRecyclerview = findViewById(R.id.user_Trips)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        getUserData()

        // userArrayList = arrayListOf<FetchTrips>()
        binding.submitBtn.setOnClickListener {
            val source = binding.sourceEt.text.toString()
            val destination = binding.destinationEt.text.toString()
            val date = binding.dateEt.text.toString()
            val time = binding.timeEt.text.toString()
            val phone = (firebaseAuth.currentUser?.phoneNumber).toString()

            val form = Form(source,destination,date,time,phone)

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

            getUserData()
        }

    }

    private fun refresh_List() {
        if( userArrayList.size > 0 ) {
            sort(userArrayList)
            userRecyclerview.adapter = tripAdapter(this@Trips, userArrayList)
        }
    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("Form")

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val user = userSnapshot.getValue(FetchTrips::class.java)
                        if( user!!.phone == (firebaseAuth.currentUser?.phoneNumber).toString()
                            && user.phone != null )
                            userArrayList.add(user)

                    }
                }
                refresh_List()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun sort(users: ArrayList<FetchTrips>) {
        val comparator = Comparator { o1: FetchTrips, o2: FetchTrips ->
            return@Comparator dateValidator(o1.date.toString(), o2.date.toString())
        }
        val copy = arrayListOf<FetchTrips>().apply { addAll(users) }
        copy.sortWith(comparator)
        userArrayList =  copy
    }

    fun dateValidator( s1: String, s2:String ) : Int {

        try {
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val date1 = formatter.parse(s1)
            val date2 = formatter.parse(s2)
            if (date1.before(date2))
                return 1
            return 0
        }
        catch(ignored: java.text.ParseException) {
            return 0
        }
    }

}


