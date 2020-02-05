package io.github.yuk7.collesure2.ui.user

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.preference.PreferenceManager
import io.github.yuk7.collesure2.R
import io.github.yuk7.collesure2.data.ImageItem
import io.github.yuk7.collesure2.data.network.EngineGoogle
import io.github.yuk7.collesure2.data.repository.AppDB
import io.github.yuk7.collesure2.data.repository.history.History
import io.github.yuk7.collesure2.ui.list.RecyclerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchActivity : AppCompatActivity() {
    var nsfw = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nsfw = intent.getBooleanExtra("nsfw",false)

        setContentView(R.layout.layout_frame_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.item_search_actionbar, menu)


        val searchView = menu!!.findItem(R.id.search_search).actionView as SearchView

        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(text: String?): Boolean {
                val engine = EngineGoogle()
                val prefs =  PreferenceManager.getDefaultSharedPreferences(applicationContext)
                GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.Default) {
                        if(prefs.getBoolean("isSaveHistory", true)) {
                            val history = History()
                            history.word = text!!
                            history.nsfw = nsfw
                            AppDB.getInstance(applicationContext).historyDao().insert(history)
                        }

                        engine.searchImage(text!!, 0, 100, nsfw)
                    }.let {
                        showResultFragment(it)
                    }
                }
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                return false
            }
        })

        searchView.setQuery(intent.getStringExtra("word")!!,true)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showResultFragment(imageList: ArrayList<ImageItem>) {
        val recyclerFragment = RecyclerFragment()
        val bundle = Bundle()
        bundle.putSerializable("imageList", imageList)
        recyclerFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frame_view, recyclerFragment).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}