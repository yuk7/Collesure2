package com.example.collesure2.ui

import android.app.ListFragment
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.example.collesure2.R
import com.example.collesure2.ui.list.ResultFragment
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    var word = ""
    var imageUrlList = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            word = editText.text.toString()


            val task = object : MyAsyncTask() {
                override fun onPostExecute(result: String?) {
                    super.onPostExecute(result)
                    intent.putExtra("list", imageUrlList)
                    intent.putExtra("word", word)

                    val fragment = ResultFragment.newInstance()
                    val fragmentManager = supportFragmentManager
                    val bundle = Bundle()
                    bundle.putStringArrayList("imageUrlList",imageUrlList)
                    fragment.arguments = bundle

                    fragmentManager.beginTransaction().replace(R.id.detailContainer, fragment).commit()
                }

            }
            task.execute()
        }
    }


    open inner class MyAsyncTask : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg p0: Void?): String? {
            return getHtml()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val regex = "<img.+?src=\"(.+?)\".+?>"
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(result)
            imageUrlList.clear()
            while (matcher.find()) {
                imageUrlList.add(matcher.group(1))
            }
        }
    }


    fun getHtml(): String {
        val client = OkHttpClient()
        val req = Request.Builder().url("http://www.google.com/search?q=${word}&tbm=isch")
            .header("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows 98)").get().build()
        val resp = client.newCall(req).execute()
        return resp.body!!.string()
    }

}