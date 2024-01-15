package com.example.socialize20.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialize20.Models.Post
import com.example.socialize20.Models.User
import com.example.socialize20.R
import com.example.socialize20.Utils.USER_NODE
import com.example.socialize20.databinding.PostRvHomeBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class HomePostAdapter(var context: Context,var postList: ArrayList<Post>):RecyclerView.Adapter<HomePostAdapter.MyHolder>() {
    inner class MyHolder(var binding:PostRvHomeBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = PostRvHomeBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            //        Setting all the fetched data over the layout we made
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {
                var user = it.toObject<User>()
//            setting the fetch image over profile
                Picasso.get().load(user!!.image).placeholder(R.drawable.profile).into(holder.binding.profileforPost)
                holder.binding.name.text = user.name
            }
        }catch (e : Exception){}
        //    Setting the post image and time
        Glide.with(context).load(postList.get(position).posturl)
            .placeholder(R.drawable.loading).into(holder.binding.postImage)
//        set time
        try {
            val text = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.time.text =text
        }
        catch (e:Exception){
            holder.binding.time.text ="Time unavailable"
        }
        holder.binding.csption.text = postList.get(position).postCaption
        holder.binding.like.setOnClickListener {
            holder.binding.like.setImageResource(R.drawable.like2)
        }
        holder.binding.share.setOnClickListener {
//            Code to share the image url on click of share button
            var i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT,postList.get(position).posturl)
            context.startActivity(i)
        }
        holder.binding.save.setOnClickListener {
            Toast.makeText(context, "Work pending......", Toast.LENGTH_SHORT).show()
        }







//        CHAT GPT's code
//        holder.binding.like.setOnClickListener {
//            val currentImageResource = holder.binding.like.drawable.constantState
//
//            val likeDrawable = ContextCompat.getDrawable(context, R.drawable.like2)?.constantState
//            val heartDrawable = ContextCompat.getDrawable(context, R.drawable.heart)?.constantState
//
//            if (currentImageResource == likeDrawable) {
//                // If the current image is the like image, change it to the heart image
//                holder.binding.like.setImageResource(R.drawable.heart)
//            } else if (currentImageResource == heartDrawable) {
//                // If the current image is the heart image, change it to the like image
//                holder.binding.like.setImageResource(R.drawable.like2)
//            }
//        }

    }
}