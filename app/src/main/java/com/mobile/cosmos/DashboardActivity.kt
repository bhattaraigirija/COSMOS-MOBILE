package com.mobile.cosmos

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.topAppBar)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                view.paddingLeft,
                systemBars.top,
                view.paddingRight,
                systemBars.bottom
            )

            insets
        }

        onBackPressedDispatcher.addCallback(this) {
            showExitDialog()
        }

        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navigationView)

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


        // Handle Drawer Menu Clicks
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                }

                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }

                R.id.nav_courses -> {
                    startActivity(Intent(this, CourseActivity::class.java))
                }

                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val profile = findViewById<LinearLayout>(R.id.layoutProfile)
        profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val course = findViewById<LinearLayout>(R.id.layoutCourses)
        course.setOnClickListener {
            val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)
        }


    }

    fun showExitDialog() {
        val exitDialog = AlertDialog.Builder(this)
        exitDialog.setTitle("Exit App")
        exitDialog.setMessage("Do you want to exit?")
        exitDialog.setPositiveButton("Yes") { _, _ ->
            finishAffinity()
        }
        exitDialog.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        exitDialog.show()

    }
}
