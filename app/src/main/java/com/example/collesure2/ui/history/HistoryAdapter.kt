package com.example.collesure2.ui.history

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collesure2.R
import com.example.collesure2.data.repository.history.History
import com.example.collesure2.ui.user.SearchActivity
import kotlinx.android.synthetic.main.search_word.view.*

class HistoryAdapter(private val context: Context, private val historyList: List<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_word, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        var word = historyList[position].word
        if(!historyList[position].nsfw) {
            word += "  (セーフサーチ)"
        }
        holder.itemView.searchWords.text = word

        holder.itemView.searchWords.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("word", historyList[position].word)
            intent.putExtra("nsfw", historyList[position].nsfw)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}
