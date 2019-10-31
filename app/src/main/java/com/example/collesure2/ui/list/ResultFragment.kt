package com.example.collesure2.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collesure2.R



class ResultFragment : Fragment() {

    companion object {
        fun newInstance(): ResultFragment {
            val fragment = ResultFragment()
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val imageUrlList = bundle!!.get("imageUrlList") as List<String>

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = ResultAdapter(context, imageUrlList)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result,container,false)
    }

}