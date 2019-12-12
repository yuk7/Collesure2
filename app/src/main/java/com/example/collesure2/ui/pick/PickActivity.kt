package com.example.collesure2.ui.pick

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.collesure2.R
import com.example.collesure2.data.ImageItem
import com.github.chrisbanes.photoview.PhotoView


class PickActivity : AppCompatActivity() {

    var imgItem = ImageItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val imageView = findViewById<PhotoView>(R.id.pickUpImage)
        imgItem = intent.getSerializableExtra("item") as ImageItem

        val circular = CircularProgressDrawable(this)
        circular.strokeWidth = 8f
        circular.centerRadius = 80f
        circular.start()

        Glide.with(this)
            .load(imgItem.imageUrl)
            .placeholder(circular)
            .error(Glide.with(this).load(imgItem.thumbIUrl)
                .error(R.drawable.ic_error_red_24dp))
            .into(imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.item_pick_actionbar, menu)

        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.pick_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, imgItem.imageUrl)
                startActivity(intent)
                return true
            }
            R.id.pick_browser -> {
                val uri = Uri.parse(imgItem.url)
                startActivity(Intent(Intent.ACTION_VIEW,uri));
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
