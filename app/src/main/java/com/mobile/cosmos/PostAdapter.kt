package com.mobile.cosmos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private val list: List<Post>) :
    RecyclerView.Adapter<PostAdapter.PostVH>() {

    class PostVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val body: TextView = view.findViewById(R.id.body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostVH(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PostVH, position: Int) {
        holder.title.text = list[position].title
        holder.body.text = list[position].body
    }
}
