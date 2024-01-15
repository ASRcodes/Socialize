package com.example.socialize20.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.socialize20.Models.Post
import com.example.socialize20.R
import com.example.socialize20.adapters.MypostRecycleAdapter
import com.example.socialize20.databinding.FragmentMypostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class mypostFragment : Fragment() {
    lateinit var binding:FragmentMypostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding =FragmentMypostBinding.inflate(inflater, container, false)

//        creating a arrayList var
        var postList = ArrayList<Post>()
//        Call the adapter and set value
        var adapter = MypostRecycleAdapter(requireContext(),postList)
//        setting up the recyclerView
        binding.rv.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
//        Setting up the value over our adapter
        binding.rv.adapter = adapter

//        Now code to fetch the value from FireBase and show it over our recyclerView
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
//            creating a arraylist to show the fetched values
            var tempList = arrayListOf<Post>()
//            fetching values using for loop
            for (i in it.documents){
//                fetching the values in snapshots objects
                var post:Post = i.toObject<Post>()!!
                tempList.add(post)
            }
            //        Adding all the values over postList from tempList
            postList.addAll(tempList)
//            notifying the recyclerView about changes
            adapter.notifyDataSetChanged()
        }
    return binding.root
    }

    companion object {
    }
}