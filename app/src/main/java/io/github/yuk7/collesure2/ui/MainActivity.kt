package io.github.yuk7.collesure2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import io.github.yuk7.collesure2.R
import io.github.yuk7.collesure2.ui.bnv.Controller
import io.github.yuk7.collesure2.ui.user.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation_view.setOnNavigationItemSelectedListener(Controller(supportFragmentManager).navigationListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment, SearchFragment()).commit()

        val prefs =  PreferenceManager.getDefaultSharedPreferences(baseContext)
        when (prefs.getString("select_theme","0")) {
            "0" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
            }
            "1" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "2" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

    }

}
