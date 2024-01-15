package com.example.socialize20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialize20.Models.Post
import com.example.socialize20.databinding.MyPostRecyclerLayoutBinding
import com.squareup.picasso.Picasso

class MypostRecycleAdapter (var context: Context,var postList: ArrayList<Post>):
    RecyclerView.Adapter<MypostRecycleAdapter.ViewHolder>() {
    inner class ViewHolder(var binding:MyPostRecyclerLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        binding the layout which we have created for recycler view
        val binding =
            MyPostRecyclerLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
//        Pass the inner class's ViewHolder here
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
     return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Here we'll get our image using picasso
        Picasso.get().load(postList.get(position).posturl).into(holder.binding.postImage)
    }
}