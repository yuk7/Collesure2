package com.example.collesure2.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collesure2.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val intent = getIntent()

        val imageUrlList = intent.getStringArrayListExtra("list")

        val recycler = findViewById<RecyclerView>(R.id.recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(baseContext, 2)
            adapter = ListAdapter(context, imageUrlList)

        }

    }


}