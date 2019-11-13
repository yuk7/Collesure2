package com.example.collesure2.ui.user

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.collesure2.R
import com.example.collesure2.data.network.EngineGoogle
import com.example.collesure2.data.network.SearchEngine
import com.example.collesure2.ui.list.RecyclerFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    var word = ""
    var nsfw = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)



        searchButton.setOnClickListener {
            word = searchWord.text.toString()
            nsfw = switch_nsfw.isChecked
            object : MyAsyncTask() {
                override fun onPostExecute(result: ArrayList<String>) {
                    super.onPostExecute(result)
                    val recyclerFragment = RecyclerFragment()
                    val fragmentManager = fragmentManager
                    val bundle = Bundle()
                    bundle.putStringArrayList("imageUrlList", result)
                    recyclerFragment.arguments = bundle
                    val transaction = fragmentManager!!.beginTransaction().apply {
                        replace(R.id.fragment, recyclerFragment)
                        addToBackStack(null)
                    }
                    transaction.commit()
                }
            }.execute()
        }
    }

    open inner class MyAsyncTask : AsyncTask<Void, Void, ArrayList<String>>() {
        override fun doInBackground(vararg p0: Void?): ArrayList<String> {
            val engine: SearchEngine = EngineGoogle()

            val imageUrlList = arrayListOf<String>()
            for (page in 0..2) {
                imageUrlList.addAll(engine.searchImage(word, page, nsfw))
            }
            return imageUrlList
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}