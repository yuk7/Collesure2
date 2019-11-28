package com.example.collesure2.ui.list

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collesure2.R
import com.example.collesure2.data.ImageItem
import com.example.collesure2.data.repository.AppDB
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class RecyclerAdapter(private val context: Context, private val imageList: List<ImageItem>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position].thumbIUrl)
            .into(holder.itemView.item_iv)

        holder.itemView.item_iv.setOnClickListener({
            val uri = Uri.parse(imageList[position].imageUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        })

        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.Default) {
                val db = AppDB.getInstance(context)
                db.favoriteDao().findByImageUrl(imageList[position].imageUrl)
            }.await().let {
                if(it == null) {
                    holder.itemView.favoriteButton.setImageResource(R.drawable.ic_favorite_border_gray_24dp)
                } else {
                    holder.itemView.favoriteButton.setImageResource(R.drawable.ic_favorite_pink_24dp)
                }
            }
        }

        holder.itemView.favoriteButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                async(Dispatchers.Default) {
                    val db = AppDB.getInstance(context)
                    val favitem = db.favoriteDao().findByImageUrl(imageList[position].imageUrl)
                    if(favitem == null) {
                        db.favoriteDao().insert(imageList[position])
                        holder.itemView.favoriteButton.setImageResource(R.drawable.ic_favorite_pink_24dp)
                    } else {
                        db.favoriteDao().deleteByImageUrl(imageList[position].imageUrl)
                        holder.itemView.favoriteButton.setImageResource(R.drawable.ic_favorite_border_gray_24dp)
                    }
                }
            }
        }

    }

}