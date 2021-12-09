
package com.example.finalprojectv1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalprojectv1.activities.AllTrips
import com.example.finalprojectv1.activities.ProfileData
import com.example.finalprojectv1.activities.Trips
import com.example.finalprojectv1.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)



        lateinit var phone:String
        firebaseAuth = FirebaseAuth.getInstance()
        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            //val message = bundle.getString("object") // 1

            phone = intent.getStringExtra("ExtraPhone").toString()
            //val isprofile = intent.getStringExtra("ExtraProfile")

            //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


        }
        else {
            phone = (firebaseAuth.currentUser?.phoneNumber).toString()

        }

//        name2.text=ename
//        address2.text=address

        readData( phone)

        binding.LogoutBtn.setOnClickListener {

            logout()

        }

    }

    private fun readData( phone : String ){
        //val p2 = (firebaseAuth.currentUser?.phoneNumber).toString()


        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(phone).get().addOnSuccessListener {

            var fName = "Guest"
            var age = "100"

            if( it.exists() ){

                fName = it.child("name").value.toString()
                age = it.child("age").value.toString()

                Toast.makeText(this@ProfileActivity, "Fetch Successful", Toast.LENGTH_SHORT).show()

            }

            binding.fullNameEt.text = fName
            binding.ageEt.text = age
            binding.phoneEt.text = phone

        }.addOnFailureListener {

            Toast.makeText(this@ProfileActivity, "Fetch Unsuccessful", Toast.LENGTH_SHORT).show()

        }

    }

    fun logout(){

        val sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply{

            val phone = binding.phoneEt.text.toString().trim()
            putString("Id",null)
            putBoolean("LoggedIn",false)

        }.apply()
        startActivity(Intent( this, LoginActivity::class.java ))
    }

}

