
package com.mobile.cosmos


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    private lateinit var db: DBHelper
    private var editUserId: Int = -1


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        db = DBHelper(this)
        db.close()


        val btnRegister = findViewById<Button>(R.id.btnRegister)


        // ---------- CHECK EDIT MODE ----------
        editUserId = intent.getIntExtra("USER_ID", -1)
        if (editUserId != -1) {
            btnRegister.text = "Update"
            loadUserForEdit(editUserId)
        }


        btnRegister.setOnClickListener {


            val name = findViewById<EditText>(R.id.edtName).text.toString()
            val address = findViewById<EditText>(R.id.edtAddress).text.toString()
            val dob = findViewById<EditText>(R.id.edtDob).text.toString()
            val email = findViewById<EditText>(R.id.edtEmail).text.toString()
            val phone = findViewById<EditText>(R.id.edtPhone).text.toString()
            val password = findViewById<EditText>(R.id.edtPassword).text.toString()


            val terms = findViewById<CheckBox>(R.id.ckCondition).isChecked
            val radioGroup = findViewById<RadioGroup>(R.id.rdGroup)
            val selectedGroup = radioGroup.checkedRadioButtonId


            val spinner = findViewById<Spinner>(R.id.dpCourse)
            val course = spinner.selectedItem.toString()


            // VALIDATION
            if (name.isEmpty()) { toast("Enter Name"); return@setOnClickListener }
            if (address.isEmpty()) { toast("Enter Address"); return@setOnClickListener }
            if (phone.length < 10) { toast("Enter valid phone"); return@setOnClickListener }


            if (selectedGroup == -1) { toast("Select Gender"); return@setOnClickListener }
            if (!terms) { toast("Accept terms"); return@setOnClickListener }


            val gender = when (selectedGroup) {
                R.id.rdMale -> "Male"
                R.id.rdFemale -> "Female"
                else -> "Other"
            }


            val ok: Boolean


            if (editUserId == -1) {
                // INSERT
                ok = db.insertUser(name, address, dob, email, phone, password, gender, course)
                if (ok) toast("Registered Successfully!") else toast("Registration Failed!")
            } else {
                // UPDATE
                val user = Users(editUserId, name, phone, email, address, dob, password, gender, course)
                ok = db.updateUser(user)
                if (ok) toast("Updated Successfully!") else toast("Update Failed!")
            }


            if (ok) {
                // go to list page
                startActivity(Intent(this, UserListActivity::class.java))
                finish()
            }
        }
    }


    private fun loadUserForEdit(userId: Int) {
        val user = db.getUserById(userId) ?: return


        findViewById<EditText>(R.id.edtName).setText(user.name)
        findViewById<EditText>(R.id.edtAddress).setText(user.address)
        findViewById<EditText>(R.id.edtDob).setText(user.dob)
        findViewById<EditText>(R.id.edtEmail).setText(user.email)
        findViewById<EditText>(R.id.edtPhone).setText(user.phone)
        findViewById<EditText>(R.id.edtPassword).setText(user.password)


        // Set gender radio
        val radioGroup = findViewById<RadioGroup>(R.id.rdGroup)
        when (user.gender) {
            "Male" -> radioGroup.check(R.id.rdMale)
            "Female" -> radioGroup.check(R.id.rdFemale)
        }


        // Set spinner course (simple way)
        val spinner = findViewById<Spinner>(R.id.dpCourse)
        val adapter = spinner.adapter
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i).toString() == user.course) {
                spinner.setSelection(i)
                break
            }
        }
    }


    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}