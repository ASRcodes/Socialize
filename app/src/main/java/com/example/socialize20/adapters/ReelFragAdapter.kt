package com.example.socialize20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialize20.Models.Reels
import com.example.socialize20.R
import com.example.socialize20.databinding.ReelFragmentDesignBinding
import com.squareup.picasso.Picasso

class ReelFragAdapter (var context: Context, var reelList: ArrayList<Reels>):RecyclerView.Adapter<ReelFragAdapter.viewHolder>() {
    inner class viewHolder(var binding: ReelFragmentDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var binding = ReelFragmentDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
//        getting user's profile image
        Picasso.get().load(reelList.get(position).profileLink).placeholder(R.drawable.profile)
            .into(holder.binding.profileIMG)
//        setting the caption of user
        val caption = reelList[position].reelCaption
        if (caption.isNotEmpty()) {
            holder.binding.caption.setText(reelList.get(position).reelCaption)
        } else {
            holder.binding.caption.text = "No caption......"
        }
//        setting the reel over videoView
        holder.binding.videoView.setVideoPath(reelList.get(position).reelurl)
        holder.binding.videoView.setOnPreparedListener {
//            Adding a progressBar while reel is not loaded
            holder.binding.progressBar.visibility = View.GONE
//            When video is prepared will start it
            holder.binding.videoView.start()
        }
        holder.binding.videoView.setOnCompletionListener {
            holder.binding.videoView.start()
        }

        // Long click to pause the video
        holder.binding.videoView.setOnLongClickListener {
            if (holder.binding.videoView.isPlaying) {
                holder.binding.videoView.pause()
            }
            true
        }

        // Touch listener to resume the video on release
        holder.binding.videoView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    holder.binding.videoView.start()
                    true
                }

                else -> false
            }

        }
    }
}