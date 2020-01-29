package com.arjavp.smack.Utlities

import android.content.Context
import android.content.SharedPreferences
import com.android.volley.toolbox.Volley

//To persist data in the device.
//We'll save key value pairs to the device.
class SharedPrefs(context: Context) {

    val PREFS_FILENAME = "prefs"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    // second arguement above is mode type, 0 is content private
    val IS_LOGGED_IN = "isLoggedIn"
    val AUTH_TOKEN = "authToken"
    val USER_EMAIL = "userEmail"
    //creating customer getter and setter
    var isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN,false)
        set(value) = prefs.edit().putBoolean(IS_LOGGED_IN, value).apply()

    var authToken: String?
        get() = prefs.getString(AUTH_TOKEN,"")
        set(value) = prefs.edit().putString(AUTH_TOKEN,value).apply()

    var userEmail: String?
        get() = prefs.getString(USER_EMAIL,"")
        set(value) = prefs.edit().putString(USER_EMAIL, value).apply()
    //creating a new request queue for every web request (in AuthService) is bad practice
    //have one request queue for whole app
    val requestQueue = Volley.newRequestQueue(context)
}
/*We can initialize this class from MainActivity as this requires  context
But if declared in MainActivity it can't be accessed from other files.
We should have this at global level using Application class.*/