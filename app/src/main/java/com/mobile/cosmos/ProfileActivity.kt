package com.mobile.cosmos

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val name = findViewById<TextView>(R.id.profileName)
        val email = findViewById<TextView>(R.id.profileEmail)
        val bio = findViewById<TextView>(R.id.profileBio)

        //getting data from another activity
        val newName= intent.getStringExtra("name")
        val newEmail = intent.getStringExtra("email")
        val newAddress = intent.getStringExtra("address")
//        val newBio = intent.getStringExtra("bio")

        //set new text
        if (!newName.isNullOrEmpty() &&
            !newEmail.isNullOrEmpty()
//            !newBio.isNullOrEmpty()
        ) {
            name.text = newName
            email.text = newEmail
//            bio.text = newBio
        }









        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


    }
}