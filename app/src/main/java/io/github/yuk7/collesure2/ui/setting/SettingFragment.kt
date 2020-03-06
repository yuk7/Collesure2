package io.github.yuk7.collesure2.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import io.github.yuk7.collesure2.R
import io.github.yuk7.collesure2.data.repository.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        findPreference<Preference>("delete_history")!!.setOnPreferenceClickListener {
            AlertDialog.Builder(context!!)
                .setTitle("検索履歴の削除")
                .setMessage("検索履歴を削除してよろしいですか？")
                .setPositiveButton("OK") { dialog, which ->
                    GlobalScope.launch(Dispatchers.Main) {
                        withContext(Dispatchers.Default) {
                            val appdb = AppDB.getInstance(context!!)
                            appdb.historyDao().deleteAll()
                        }
                    }
                }
                .setNegativeButton("Cancel", null).show()
            return@setOnPreferenceClickListener true
        }

        findPreference<ListPreference>("select_theme")!!.setOnPreferenceChangeListener { preference, newValue ->
            when (newValue.toString()) {
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
            return@setOnPreferenceChangeListener true
        }
    }

}
