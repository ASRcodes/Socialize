package com.example.socialize20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialize20.Models.User
import com.example.socialize20.R
import com.example.socialize20.databinding.FollowRvBinding

class FollowAdapter(var context: Context,var followList: ArrayList<User>):RecyclerView.Adapter<FollowAdapter.Holder>() {
    inner class Holder(var binding:FollowRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FollowRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
     return followList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
//     Here we'll get the user's profile and name ,And put on the created layout
        Glide.with(context).load(followList.get(position).image).placeholder(R.drawable.profile).into(holder.binding.profileIMG)
        holder.binding.name.text = followList.get(position).name
    }
}