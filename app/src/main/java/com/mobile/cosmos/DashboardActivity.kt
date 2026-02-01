package com.mobile.cosmos

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Fragment
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView


    private fun backgroundTask(){
        val thread = Thread{
            Thread.sleep(7000)
            runOnUiThread {
                Toast.makeText(this,"Task Complete", Toast.LENGTH_LONG).show()
            }
        }
        thread.start()
    }


    override fun attachBaseContext(newBase: Context) {
        val lang = LocaleHelper().getLanguage(newBase)
        val context = LocaleHelper().setLocale(newBase,lang)
        super.attachBaseContext(context)
    }


    @SuppressLint("MissingInflatedId")
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

        //start service
        val intent = Intent(this, MyService::class.java)
        startService(intent)

        //stop service
        val stopService = findViewById<Button>(R.id.btnStopService)
        stopService.setOnClickListener {
            stopService(intent)
            backgroundTask()
        }


        val btnEnglish = findViewById<Button>(R.id.buttonEng)
        val btnNepali = findViewById<Button>(R.id.buttonNep)

        btnEnglish.setOnClickListener {
            LocaleHelper().setLocale(this,"en")
            recreate()
        }

        btnNepali.setOnClickListener {
            LocaleHelper().setLocale(this,"ne")
            recreate()
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
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }

        val course = findViewById<LinearLayout>(R.id.layoutCourses)
        course.setOnClickListener {
            val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)
        }

        val result = findViewById<LinearLayout>(R.id.layoutResult)
        val attendance = findViewById<LinearLayout>(R.id.layoutAttendance)
        val dashboardContent = findViewById<LinearLayout>(R.id.dashboardContent)
        val btnRegister = findViewById<Button>(R.id.btnRegisterStudent)
        val btnNotification = findViewById<Button>(R.id.bthShowNotification)

        btnNotification.setOnClickListener {
            showNotification()
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        result.setOnClickListener {
            showDemoFragment(Result())
            dashboardContent.visibility = View.GONE
        }

        attendance.setOnClickListener {
            showDemoFragment(Attendance())
            dashboardContent.visibility = View.GONE
        }

    }

    fun showNotification(){
        val channelId = "demo_channel"
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Test Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        val notification= NotificationCompat.Builder(this, channelId)
            .setContentTitle("This is notification")
            .setContentText("This is description of notification")
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        getSystemService(NotificationManager::class.java).notify(1,notification)

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

    fun showDemoFragment(fragment: androidx.fragment.app.Fragment){
        val container = findViewById<FrameLayout>(R.id.demoFragment)
        container.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            )
            .add(R.id.demoFragment,fragment)
            .addToBackStack(null)
            .commit()
    }
}
