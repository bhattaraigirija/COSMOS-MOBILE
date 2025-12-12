package com.mobile.cosmos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_course)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val course = listOf(
            "Android Development",
            "Python Development",
            "Data Structure",
            "Machine Learning",
            "Big Data",
            "Database Management",
        )

        val listView = findViewById<ListView>(R.id.courseList)

        //setting list data to list view UI

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            course
        )
        listView.adapter= adapter

    }
}