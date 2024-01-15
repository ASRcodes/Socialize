package com.example.socialize20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialize20.R
import com.example.socialize20.Utils.FOLLOW
import com.example.socialize20.databinding.SearchRvBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SearchAdapter(var context: Context,var userList: ArrayList<com.example.socialize20.Models.User>):RecyclerView.Adapter<SearchAdapter.Holder>() {
    inner class Holder(var binding:SearchRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var binding = SearchRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
    return userList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        Variable to get follow or unfollowed ones
        var isFollow =  false
        Glide.with(context).load(userList.get(position).image).placeholder(R.drawable.profile).into(holder.binding.profileIMG)
        holder.binding.name.text = userList.get(position).name
//        now here we will set the button as unfollow once that user's email is saved in my current user's follow field
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW)
            .whereEqualTo("email",userList.get(position).email).get()
            .addOnSuccessListener {
                if (it.documents.size==0){
//                    if document's size is zero i.e, there is no one in current user's follow field
                    isFollow=false
                }
                else{
                    holder.binding.follow.text = "Unfollow"
                    isFollow=true
                }
            }
        holder.binding.follow.setOnClickListener {
//            if the user have followed and then click the unfollow button we have to delete him from the current user's field
            if (isFollow){
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW)
                    .whereEqualTo("email",userList.get(position).email).get().addOnSuccessListener {
//                    we'll go inside the firebase field and delete that user
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW)
                        .document(it.documents.get(0).id).delete()
                    holder.binding.follow.text = "Follow"
                    isFollow= false
                }
            }
            else{
//                On click of this follow button we'll create a new field in firebase in which the followed users data will be stored
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW)
                    .document().set(userList.get(position))
//            And will set the text to Unfollow
                holder.binding.follow.text = "Unfollow"
                isFollow=true
            }
        }
    }
}