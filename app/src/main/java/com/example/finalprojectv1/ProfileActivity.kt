
package com.example.finalprojectv1

import android.content.Context
import android.content.Intent
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalprojectv1.databinding.ActivityProfileBinding
import com.example.finalprojectv1.databinding.ProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class ProfileActivity : AppCompatActivity() {


    private lateinit var binding: ProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileBinding.inflate(layoutInflater)
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

            var fName = "Guesst"
            var AadharCard = "9898 8989 8989"
            var PanCard = "CNZNHJ898"
            var EmailID = "johndoe@gmail.com"
            var PhoneNumber = "98989000000"
            var EmergencyNumber = "98989XXXXXX"
            var Address= "abc abc abc"
            var Profession = "Student"



            if( it.exists() ){

                fName = it.child("name").value.toString()
                AadharCard = it.child("aadhaar_card").value.toString()
                PanCard = it.child("pan_card").value.toString()
                EmailID = it.child("email_id").value.toString()
                PhoneNumber = it.child("phone_number").value.toString()
                EmergencyNumber = it.child("emergency_contact_number").value.toString()
                Address= it.child("address").value.toString()
                Profession = it.child("profession").value.toString()


                Toast.makeText(this@ProfileActivity, "Fetch Successful", Toast.LENGTH_SHORT).show()

            }

            binding.tvName.text = fName
            binding.tvAddress.text = Address
            binding.tvPhoneNumber.text = PhoneNumber
            binding.tvAdharCard.text = AadharCard
            binding.tvPanCard.text=PanCard
            binding.tvEmergencyContact.text=EmergencyNumber
            binding.tvEmailAddress.text=EmailID
            binding.tvProfession.text=Profession

        }.addOnFailureListener {

            Toast.makeText(this@ProfileActivity, "Fetch Unsuccessful", Toast.LENGTH_SHORT).show()

        }

    }

    fun logout(){

        val sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply{

            //val phone = binding.tvPhoneNumber.text.toString().trim()
            putString("Id",null)
            putBoolean("LoggedIn",false)

        }.apply()
        startActivity(Intent( this, LoginActivity::class.java ))
    }

}
