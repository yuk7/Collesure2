package com.example.collesure2.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collesure2.R
import com.example.collesure2.data.repository.history.History
import kotlinx.android.synthetic.main.search_word.view.*

class HistoryAdapter(private val context: Context, private val historyList: List<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_word, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemView.searchWords.setText(historyList.get(position).word)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}
