package com.example.collesure2.ui.bnv

import androidx.fragment.app.FragmentManager
import com.example.collesure2.R
import com.example.collesure2.ui.user.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Controller (val fm:FragmentManager){ //bottom naviation view用

    val navigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        //clear BackStack
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }

        when (item.itemId) {
            R.id.home -> {
                fm.beginTransaction()
                    .replace(R.id.fragment, SearchFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}