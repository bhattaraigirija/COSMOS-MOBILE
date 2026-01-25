package com.mobile.cosmos

import android.annotation.SuppressLint
import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    private lateinit var db: DBHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnRegister = findViewById<Button>(R.id.btnRegister)

        db= DBHelper(this)
        db.close()

        btnRegister.setOnClickListener {
            //for edit text
            val name = findViewById<EditText>(R.id.edtName).text
            val address = findViewById<EditText>(R.id.edtAddress).text
            val dob = findViewById<EditText>(R.id.edtDob).text
            val email = findViewById<EditText>(R.id.edtEmail).text
            val phone = findViewById<EditText>(R.id.edtPhone).text
            val password = findViewById<EditText>(R.id.edtPassword).text
            //for checkbox
            val terms = findViewById<CheckBox>(R.id.ckCondition).isChecked
            //for radiobutton
            val radioGroup= findViewById<RadioGroup>(R.id.rdGroup)
            val selectedGroup= radioGroup.checkedRadioButtonId
            //for spinner
            val spinner = findViewById<Spinner>(R.id.dpCourse)
            val course = spinner.selectedItem.toString()

            //** --------------VALIDATION-----------**//
            if(name.isEmpty()){
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(address.isEmpty()){
                Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(phone.length<10){
                Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!terms){
                Toast.makeText(this, "Accept terms and conditions.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(selectedGroup == -1){
                Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //**--------Insert users--------**//
            val ok : Boolean
            ok = db.insertUser(
                name.toString(),
                address.toString(),
                dob.toString(),
                email.toString(),
                phone.toString(),
                password.toString(),
                "null",
                course.toString()
            )
            if(ok){
                Toast.makeText(this,"Inserted Successfully", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
            }


        }

    }
}