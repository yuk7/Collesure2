package com.example.collesure2.ui.history

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collesure2.R
import com.example.collesure2.data.repository.AppDB
import com.example.collesure2.data.repository.history.History
import com.example.collesure2.ui.user.SearchActivity
import kotlinx.android.synthetic.main.search_word.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryAdapter(private val context: Context, private val historyList: List<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val historyMList = historyList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_word, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        var word = historyMList[position].word
        if(!historyMList[position].nsfw) {
            word += "  (セーフサーチ)"
        }
        holder.itemView.searchWords.text = word

        holder.itemView.searchWords.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("word", historyMList[holder.layoutPosition].word)
            intent.putExtra("nsfw", historyMList[holder.layoutPosition].nsfw)
            it.context.startActivity(intent)
        }

        holder.itemView.history_delete.setOnClickListener {
            val history = historyMList[holder.layoutPosition]
            notifyItemRemoved(holder.adapterPosition)
            historyMList.removeAt(holder.layoutPosition)

            GlobalScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.Default) {
                    val appdb = AppDB.getInstance(context!!)
                    appdb.historyDao().deleteByWordNsfw(history.word, history.nsfw)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return historyMList.size
    }
}
