package io.github.yuk7.collesure2.ui.bnv

import androidx.fragment.app.FragmentManager
import io.github.yuk7.collesure2.R
import io.github.yuk7.collesure2.ui.favorite.FavoriteFragment
import io.github.yuk7.collesure2.ui.history.HistoryFragment
import io.github.yuk7.collesure2.ui.setting.SettingFragment
import io.github.yuk7.collesure2.ui.user.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Controller(private val fm: FragmentManager) { //bottom navigation view用

    private var now = R.id.home

    val navigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        //clear BackStack
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }

        when (item.itemId) {
            R.id.home -> {
                if(now != R.id.home) {
                    fm.beginTransaction()
                        .replace(R.id.fragment, SearchFragment())
                        .commit()
                }
                now = R.id.home
                return@OnNavigationItemSelectedListener true
            }
            R.id.favorite -> {
                if(now != R.id.favorite) {
                    fm.beginTransaction()
                        .replace(R.id.fragment, FavoriteFragment())
                        .commit()
                }
                now = R.id.favorite
                return@OnNavigationItemSelectedListener true
            }
            R.id.history -> {
                if(now != R.id.history) {
                    fm.beginTransaction()
                        .replace(R.id.fragment, HistoryFragment())
                        .commit()
                }
                now = R.id.history
                return@OnNavigationItemSelectedListener true
            }
            R.id.others -> {
                if(now != R.id.others) {
                    fm.beginTransaction()
                        .replace(R.id.fragment, SettingFragment())
                        .commit()
                }
                now = R.id.others
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}