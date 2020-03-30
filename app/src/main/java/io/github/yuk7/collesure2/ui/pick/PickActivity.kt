package io.github.yuk7.collesure2.ui.pick

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.github.yuk7.collesure2.R
import io.github.yuk7.collesure2.data.ImageItem
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random


class PickActivity : AppCompatActivity() {

    private var imgItem = ImageItem()

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
                Glide.with(this)
                    .asBitmap()
                    .load(imgItem.imageUrl)
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            GlobalScope.launch(Dispatchers.Main) {
                                withContext(Dispatchers.Default) {
                                    cacheToPNG(resource)
                                }.let {
                                    val intent = Intent(Intent.ACTION_SEND)
                                        .setType("image/png")
                                        .putExtra(Intent.EXTRA_TEXT, imgItem.imageUrl)
                                        .putExtra(Intent.EXTRA_STREAM, it)
                                    val chooser = Intent.createChooser(intent, "Share with")
                                    startActivity(chooser)
                                }
                            }
                        }
                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            val intent = Intent(Intent.ACTION_SEND)
                                .setType("text/plain")
                                .putExtra(Intent.EXTRA_TEXT, imgItem.imageUrl)
                            val chooser = Intent.createChooser(intent, "Share with")
                            startActivity(chooser)
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {

                        }
                    })
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

    private fun cacheToPNG(bitmap: Bitmap): Uri {
        val cachePath = File(applicationContext.cacheDir, "images")
        cachePath.mkdirs()
        val filePath = File(cachePath, Random.nextInt().toString() + ".png")
        val fos = FileOutputStream(filePath.absolutePath)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.close()

        return FileProvider.getUriForFile(applicationContext, "$packageName.fileprovider", filePath)
    }
}
