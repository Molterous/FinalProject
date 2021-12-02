package com.example.finalprojectv1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalprojectv1.activities.Trips
import com.example.finalprojectv1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileBtn.setOnClickListener {
            startActivity(Intent( this, ProfileActivity::class.java ))
        }

        binding.addTripsBtn.setOnClickListener {
            startActivity(Intent(this, Trips::class.java))
        }

    }
}