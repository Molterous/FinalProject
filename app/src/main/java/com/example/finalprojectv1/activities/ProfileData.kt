

package com.example.finalprojectv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.finalprojectv1.MainActivity
import com.example.finalprojectv1.R
import com.example.finalprojectv1.databinding.ActivityProfileDataBinding
import com.example.finalprojectv1.utils.UserDetail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileData : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDataBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityProfileDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth =  FirebaseAuth.getInstance()
        readData()

        binding.detailSubmitBtn.setOnClickListener {

            val fullName = binding.fullNameEt.text.toString()
            val age = binding.ageEt.text.toString()
            val phone = (firebaseAuth.currentUser?.phoneNumber).toString()

            database = FirebaseDatabase.getInstance().getReference("Users")
            val user = UserDetail( fullName, age, phone )

            database.child( phone ).setValue(user).addOnSuccessListener {

                binding.fullNameEt.text.clear()
                binding.ageEt.text.clear()

                Toast.makeText(this@ProfileData, "Profile Data Set Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent( this, MainActivity::class.java ))

            }.addOnFailureListener{

                Toast.makeText(this@ProfileData, "Profile Data Set Unsuccessful", Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun readData(){

        val phone = (firebaseAuth.currentUser?.phoneNumber).toString()
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(phone).get().addOnSuccessListener {


            if( it.exists() ){

                val fName = it.child("name").value.toString()
                val age = it.child("age").value.toString()

                val editName: EditText = findViewById(R.id.fullNameEt)
                editName.setText(fName)
                val editAge: EditText = findViewById(R.id.ageEt)
                editAge.setText(age)


            }

        }.addOnFailureListener {
        }

    }

}
