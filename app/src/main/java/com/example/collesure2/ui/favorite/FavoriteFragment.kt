package com.example.collesure2.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.collesure2.R
import com.example.collesure2.data.ImageItem
import com.example.collesure2.data.repository.AppDB
import com.example.collesure2.ui.list.RecyclerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.Default) {
                val db = AppDB.getInstance(context!!)
                db.favoriteDao().getAll()
            }.await().let {
                var imageList = arrayListOf<ImageItem>()
                for (fav in it) {
                    imageList.add(ImageItem.fromFavorite(fav))
                }


                val recyclerFragment = RecyclerFragment()
                val fragmentManager = fragmentManager
                val bundle = Bundle()
                bundle.putSerializable("imageList", imageList)
                recyclerFragment.arguments = bundle
                fragmentManager!!.beginTransaction().replace(R.id.fragment, recyclerFragment)
                    .commit()
            }
        }
    }
}