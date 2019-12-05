package com.example.collesure2.ui.list

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.collesure2.R
import com.example.collesure2.data.ImageItem
import com.example.collesure2.data.repository.AppDB
import com.example.collesure2.ui.MainActivity
import com.example.collesure2.ui.pick.PickActivity
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class RecyclerAdapter(private val context: Context, private val imageList: List<ImageItem>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val circular = CircularProgressDrawable(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        circular.strokeWidth = 8f
        circular.centerRadius = 50f
        circular.start()

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position].thumbIUrl)
            .placeholder(circular)
            .error(Glide.with(context).load(imageList[position].imageUrl)
                .error(R.drawable.ic_error_red_24dp))
            .into(holder.itemView.item_iv)

        holder.itemView.item_iv.setOnClickListener {
            val intent = Intent(it.context,PickActivity::class.java)
            intent.putExtra("item",imageList[position])
            it.context.startActivity(intent)
        }

        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.Default) {
                val db = AppDB.getInstance(context)
                db.favoriteDao().findByImageUrl(imageList[position].imageUrl)
            }.await().let {
                if (it == null) {
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
                    if (favitem == null) {
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