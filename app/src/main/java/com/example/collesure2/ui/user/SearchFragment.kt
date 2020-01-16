package com.example.collesure2.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.collesure2.R
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    var word = ""
    var nsfw = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)

        searchButton.setOnClickListener {
            word = searchWord.text.toString()
            nsfw = !switch_safe.isChecked

            val intent = Intent(context,SearchActivity::class.java)
            intent.putExtra("word", word)
            intent.putExtra("nsfw", nsfw)
            startActivity(intent)
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