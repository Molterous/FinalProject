package com.example.finalprojectv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalprojectv1.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        val phone = (firebaseAuth.currentUser?.phoneNumber).toString()

        readData( phone );

    }

    private fun readData( phone : String ){

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

}