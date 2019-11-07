package com.example.collesure2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collesure2.R
import com.example.collesure2.ui.user.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment, SearchFragment()).commit()
    }

}
