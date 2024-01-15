package com.example.socialize20.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.socialize20.Models.Post
import com.example.socialize20.Models.Reels
import com.example.socialize20.R
import com.example.socialize20.Utils.REEl
import com.example.socialize20.adapters.MyReelRvAdapter
import com.example.socialize20.adapters.MypostRecycleAdapter
import com.example.socialize20.databinding.FragmentMyReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class myReelsFragment : Fragment() {
    lateinit var binding: FragmentMyReelsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyReelsBinding.inflate(inflater, container, false)

//        creating a arrayList var
        var reelList = ArrayList<Reels>()
//        Call the adapter and set value
        var adapter = MyReelRvAdapter(requireContext(),reelList)
//        setting up the recyclerView
        binding.rv.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//        Setting up the value over our adapter
        binding.rv.adapter = adapter

//        Now code to fetch the value from FireBase and show it over our recyclerView
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEl).get().addOnSuccessListener {
//            creating a arraylist to show the fetched values
            var tempList = arrayListOf<Reels>()
//            fetching values using for loop
            for (i in it.documents){
//                fetching the values in snapshots objects
                var reels: Reels = i.toObject<Reels>()!!
                tempList.add(reels)
            }
            //        Adding all the values over reelList from tempList
            reelList.addAll(tempList)
//            notifying the recyclerView about changes
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {
    }
}