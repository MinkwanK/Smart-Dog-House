package com.example.schoolproject

import android.content.Context
import android.content.SharedPreferences

class MySharedPrefrences(context: Context){
    private val prefsFilename = "prefs"
    private val prefsKeyPetName = "MyPetName"
    private val prefsKeyPetAge = "MyPetAge"
    private val prefsKeyPetSpecies = "MyPetSpecies"
    private val prefsKeyPet = "MyPet"
    private val prefsKeyPetWeight = "MyPetWeight"
    private val prefsKeyBirthMonth = "MyPetBirthMonth"
    private val prefsKeyBirthDay = "MyPetBirthDay"
    private val prefsKeyProfileCheck = "MyProfileCheck"
    private val prefsKeyFeedTime = "MyPetFeedTime"
    private val prefsKeyFeed = "MyPetFeed"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)

    var myPetName:String?
        get() = prefs.getString(prefsKeyPetName,"")
        set(value) = prefs.edit().putString(prefsKeyPetName,value).apply()

    var myPetAge : Int
        get() = prefs.getInt(prefsKeyPetAge,1)
        set(value) = prefs.edit().putInt(prefsKeyPetAge,value).apply()

    var myPetWeight : Float
        get() = prefs.getFloat(prefsKeyPetWeight,0.0f)
        set(value) = prefs.edit().putFloat(prefsKeyPetWeight,value).apply()

    var myPet : Boolean
        get() = prefs.getBoolean(prefsKeyPet,true)
        set(value) = prefs.edit().putBoolean(prefsKeyPet,value).apply()

    var myPetBirthMonth:Int
        get() = prefs.getInt(prefsKeyBirthMonth,1)
        set(value) = prefs.edit().putInt(prefsKeyBirthMonth,value).apply()

    var myPetBirthDay:Int
        get() = prefs.getInt(prefsKeyBirthDay,1)
        set(value) = prefs.edit().putInt(prefsKeyBirthDay,value).apply()

    var myProfileCheck:Boolean
        get() = prefs.getBoolean(prefsKeyProfileCheck,false)
        set(value) = prefs.edit().putBoolean(prefsKeyProfileCheck,value).apply()

    var myPetFeedTime:Int
        get() = prefs.getInt(prefsKeyFeedTime,30)
        set(value) = prefs.edit().putInt(prefsKeyFeedTime,value).apply()

    var myPetFeed:Int
        get() = prefs.getInt(prefsKeyFeed,0)
        set(value) = prefs.edit().putInt(prefsKeyFeed,value).apply()

}
