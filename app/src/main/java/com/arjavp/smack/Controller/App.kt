package com.arjavp.smack.Controller
import android.app.Application
import com.arjavp.smack.Utlities.SharedPrefs

class App: Application() {

    companion object{
        lateinit var prefs: SharedPrefs
    }
    //like a singleton but for inside a specific class
    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}
// refactored authToken, userEmail and isLoggedIn to App.prefs.(--) in AuthService, MessageService, MainActivity, etc.