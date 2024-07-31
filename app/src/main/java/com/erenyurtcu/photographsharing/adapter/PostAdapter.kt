package com.erenyurtcu.photographsharing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erenyurtcu.photographsharing.databinding.FragmentFeedBinding
import com.erenyurtcu.photographsharing.databinding.RecyclerRowBinding
import com.erenyurtcu.photographsharing.model.Post
import com.squareup.picasso.Picasso

class PostAdapter (private val postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostHolder>() {
    class PostHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val positionPost = postList[position]

        holder.binding.recyclerCommentText.text = positionPost.comment
        holder.binding.recyclerEmailText.text = positionPost.email
        Picasso.get().load(positionPost.downloadUrl).into(holder.binding.recyclerImageView)
    }
}