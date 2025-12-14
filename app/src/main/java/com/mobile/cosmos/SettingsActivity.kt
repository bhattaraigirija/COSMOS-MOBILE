package com.mobile.cosmos

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        val settingBack = findViewById<TextView>(R.id.tvTitle)
        val themeButton = findViewById<Button>(R.id.btnChangeTheme)
        val aboutButton = findViewById<Button>(R.id.btnAbout)
        val navigateProfile = findViewById<Button>(R.id.btnNavigateToProfile)

        settingBack.setOnClickListener { onBackPressed() }
        themeButton.setOnClickListener { showThemeDialoge() }
        aboutButton.setOnClickListener { showAboutDialoge() }
        navigateProfile.setOnClickListener { sendData() }

    }
}