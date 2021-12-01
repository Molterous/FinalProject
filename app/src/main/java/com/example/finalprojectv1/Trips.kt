package com.example.finalprojectv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalprojectv1.databinding.ActivityLoginBinding
import com.example.finalprojectv1.databinding.ActivityMainBinding
import com.example.finalprojectv1.databinding.ActivityTripsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Trips : AppCompatActivity() {

    private lateinit var binding : ActivityTripsBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)
        binding = ActivityTripsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            val source = binding.sourceEt.text.toString()
            val destination = binding.destinationEt.text.toString()
            val date = binding.dateEt.text.toString()
            val time = binding.timeEt.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Form")
            val form = Form(source,destination,date,time)
            database.child(destination).setValue(form).addOnSuccessListener {
                binding.sourceEt.text.clear()
                binding.destinationEt.text.clear()
                binding.dateEt.text.clear()
                binding.timeEt.text.clear()

                Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show()
            }
        }
    }
}