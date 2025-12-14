package com.mobile.cosmos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
        themeButton.setOnClickListener { showThemeDialog() }
        aboutButton.setOnClickListener { showAboutDialoge() }
        navigateProfile.setOnClickListener { sendData() }
    }
    private fun showThemeDialog() {
        val themeOption = arrayOf("Light Theme", "Dark Theme")
        val dialogB = AlertDialog.Builder(this)
        dialogB.setTitle("Select Theme")
        dialogB.setItems(themeOption) { _, selectedItem ->
                if (selectedItem == 0) {
                    Toast.makeText(this, "Light Theme Selected",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Dark Theme Selected",
                        Toast.LENGTH_SHORT).show()

            }
        }
        dialogB.setNegativeButton("Cancel") { dialog, _ -> {
                dialog.dismiss()
            }
        }
        dialogB.show()

    }

    private fun showAboutDialoge(){
        val dialogA = AlertDialog.Builder(this)
        dialogA.setTitle("About App")
        dialogA.setMessage(
            "this is about section of our app this is about section of our app \nthis is about section of our app \n this is about section of our appthis is about section of our appthis is about section of our appthis is about section of our appthis is about section of our app\nthis is about section of our appthis is about section of our appthis is about section of our app"
        )
        dialogA.setPositiveButton("OK", null)
        dialogA.show()

    }

    private fun sendData(){
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("name", "Sandip Katwal")
        intent.putExtra("email","sandip@gmail.com")
        intent.putExtra("bio","Mobile Application Developer")
        startActivity(intent)
    }

}