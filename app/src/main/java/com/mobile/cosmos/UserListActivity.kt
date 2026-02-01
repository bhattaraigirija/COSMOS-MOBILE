package com.mobile.cosmos


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class UserListActivity : AppCompatActivity() {


    private lateinit var db: DBHelper
    private lateinit var listView: ListView
    private lateinit var users: ArrayList<Users>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)


        supportActionBar?.setDisplayHomeAsUpEnabled(true) // back button in top bar


        db = DBHelper(this)
        listView = findViewById(R.id.listViewUsers)


        loadData()


        listView.setOnItemClickListener { _, _, position, _ ->
            val clickedUser = users[position]


            AlertDialog.Builder(this)
                .setTitle("What do you want?")
                .setItems(arrayOf("Edit", "Delete")) { _, which ->
                    if (which == 0) {
                        // EDIT
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("USER_ID", clickedUser.id)
                        startActivity(intent)
                    } else {
                        // DELETE
                        val ok = db.deleteUser(clickedUser.id)
                        if (ok) {
                            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show()
                            loadData()
                        } else {
                            Toast.makeText(this, "Delete failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .show()
        }
    }


    private fun loadData() {
        users = db.getAllUsers()


        // show simple text in listview
        val displayList = users.map { "${it.name} - ${it.phone}" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)
        listView.adapter = adapter
    }


    // ActionBar back button
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}