package com.appham.postlister.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appham.postlister.R
import com.appham.postlister.model.data.Post
import com.appham.postlister.viewmodel.MainViewModel

class PostsAdapter(private val viewModel: MainViewModel) : ListAdapter<Post, PostsAdapter.PostViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        holder.txtTitle.text = item.title
        holder.txtBody.text = item.body
        holder.cardImgUser.setOnClickListener {
            viewModel.navigate(item)
        }
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.txtPostTitle)
        val txtBody: TextView = itemView.findViewById(R.id.txtPostBody)
        val cardImgUser: CardView = itemView.findViewById(R.id.cardImgPost)
    }
}

private class TransactionDiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}