package com.example.echohealth.Profile
import android.content.Context
import android.content.SharedPreferences
import android.util.Log


fun saveUserData(User: UserData, context: Context) {
    val preferences: SharedPreferences = context.getSharedPreferences("userpref", Context.MODE_PRIVATE)
    with(preferences.edit()) {
        putString("name", User.name)
        putString("email",User.email)
        putString("gender", User.gender)
        putInt("age", User.age)
        apply()
    }
    Log.d("UserData", "User data saved: $User")
}

