package com.example.schoolproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        if(App.prefs.myProfileCheck == true) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else
        {
            intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}