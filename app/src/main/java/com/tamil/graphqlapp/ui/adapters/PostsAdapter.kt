package com.tamil.graphqlapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tamil.graphqlapp.databinding.ListItemPostBinding
import com.tamil.graphqlapp.ui.posts.data.PostsAdapterItem
import java.util.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PostsAdapterItem>() {
        override fun areItemsTheSame(
            oldItem: PostsAdapterItem,
            newItem: PostsAdapterItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PostsAdapterItem,
            newItem: PostsAdapterItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    private var onItemClickListener: ((PostsAdapterItem, View) -> Unit)? = null

    fun setOnItemClickListener(listener: (PostsAdapterItem, View) -> Unit) {
        onItemClickListener = listener
    }

    var posts: List<PostsAdapterItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    class PostsViewHolder(val binding: ListItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding =
            ListItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.binding.postTitle.text = posts[position].title?.capitalize(Locale.getDefault())
        holder.binding.postContent.text =
            posts[position].content?.capitalize(Locale.getDefault())
        holder.binding.root.setOnClickListener { view ->
            onItemClickListener?.let { listener ->
                listener(posts[position], view)
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}