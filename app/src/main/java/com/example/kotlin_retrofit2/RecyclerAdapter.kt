package com.example.kotlin_retrofit2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
    ) {
        private val title = itemView.title

        fun onBind(data: Data.BoxOfficeResult.DailyBoxOffice) {
            this.title.text = data.movieNm
        }
    }

    private val items = mutableListOf<Data.BoxOfficeResult.DailyBoxOffice>()

    fun changeData(items: List<Data.BoxOfficeResult.DailyBoxOffice>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }
}