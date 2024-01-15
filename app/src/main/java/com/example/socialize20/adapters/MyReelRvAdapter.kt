package com.example.socialize20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.socialize20.Models.Reels
import com.example.socialize20.databinding.MyPostRecyclerLayoutBinding

class MyReelRvAdapter (var context: Context, var reelList: ArrayList<Reels>):
        RecyclerView.Adapter<MyReelRvAdapter.ViewHolder>() {
        inner class ViewHolder(var binding: MyPostRecyclerLayoutBinding): RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        binding the layout which we have created for recycler view
            val binding =
                MyPostRecyclerLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
//        Pass the inner class's ViewHolder here
            return ViewHolder(binding)
        }

    override fun getItemCount(): Int {
            return reelList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Here we'll get our video url using glide
            Glide.with(context).load(reelList.get(position).reelurl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.postImage)
        }
    }
