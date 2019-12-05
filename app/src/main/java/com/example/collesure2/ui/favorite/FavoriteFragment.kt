package com.example.collesure2.ui.favorite

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.collesure2.R
import com.example.collesure2.data.ImageItem
import com.example.collesure2.data.repository.AppDB
import com.example.collesure2.data.repository.favorite.Favorite
import com.example.collesure2.ui.list.RecyclerFragment
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.coroutines.*

class FavoriteFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.Default) {
                searchFavorite("")
            }.await().let {
                showRecycler(it)
            }
        }

        favorite_word.setOnKeyListener { v, keyCode, event ->
            if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                val manager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.Default) {
                        searchFavorite(favorite_word.text.toString())
                    }.let {
                        showRecycler(it)
                    }
                }
                true
            }else {
                false
            }
        }
    }

    private fun showRecycler(imageList: ArrayList<ImageItem>) {
        val recyclerFragment = RecyclerFragment()
        val fragmentManager = fragmentManager
        val bundle = Bundle()
        bundle.putSerializable("imageList", imageList)
        recyclerFragment.arguments = bundle
        fragmentManager!!.beginTransaction().replace(R.id.favorite_view, recyclerFragment).commit()

    }
    private fun searchFavorite(word: String): ArrayList<ImageItem> {
        var query = "%" + word.split(" ").joinToString(separator = "%") + "%"

        val db = AppDB.getInstance(context!!)
        var list: List<Favorite> = listOf<Favorite>()
        if(query == "") {
            list = db.favoriteDao().getAll()
        } else {
            list = db.favoriteDao().searchByTag(query)
        }

        val imageList = arrayListOf<ImageItem>()

        for (fav in list) {
            imageList.add(ImageItem.fromFavorite(fav))
        }
        return imageList
    }
}
