package com.kuldeep.loginauthdemo.view.acitivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.kuldeep.loginauthdemo.R
import com.kuldeep.loginauthdemo.data.UserPreferences
import com.kuldeep.loginauthdemo.util.startNewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view=setContentView(R.layout.activity_main)
        val userPreferences=UserPreferences(this)
        userPreferences.authToken.asLiveData().observe(this, Observer {
            val activity= if(it==null) AuthActivity::class.java else HomeActivity::class.java
            startNewActivity(activity)
        })
        //finish()
        return view
    }
}