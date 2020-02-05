package io.github.yuk7.collesure2.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.yuk7.collesure2.R
import io.github.yuk7.collesure2.data.repository.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Default) {
                val appdb = AppDB.getInstance(context!!)
                appdb.historyDao().getGroup()
            }.let {
                view.findViewById<RecyclerView>(R.id.recyclerview).apply {
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(context, 1)
                    adapter = HistoryAdapter(context, it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return return inflater.inflate(R.layout.fragment_recycler, container, false)
    }
}