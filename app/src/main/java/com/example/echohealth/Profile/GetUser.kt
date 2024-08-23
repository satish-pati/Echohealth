package com.example.echohealth.Profile

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
fun fetchUserData(context: Context): UserData? {
    val preferences: SharedPreferences = context.getSharedPreferences("userpref", Context.MODE_PRIVATE)
    val name = preferences.getString("name", null)
    val email=preferences.getString("email",null)
    val gender = preferences.getString("gender", null)
    val age = preferences.getInt("age", 0)
    when(name != null && gender != null && age != 0 &&email!=null) {
        true->{
            Log.d("UserData", "Fetched data - Name: $name, Email: $email, Gender: $gender, Age: $age")

            return   UserData(name,email, gender, age)
        }
        false->{
            Log.d("UserData", "User data fetch failed or incomplete")
            return null
        }
    }
}

