package com.example.schoolproject
import android.app.Application
import android.content.SharedPreferences

class App : Application(){
    companion object{
        lateinit var prefs :MySharedPrefrences
    }

    override fun onCreate() {
        prefs = MySharedPrefrences(applicationContext)
        super.onCreate()
    }
}
