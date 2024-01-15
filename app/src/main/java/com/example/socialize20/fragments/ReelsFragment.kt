package com.example.socialize20.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.socialize20.Models.Reels
import com.example.socialize20.R
import com.example.socialize20.Utils.REEl
import com.example.socialize20.adapters.ReelFragAdapter
import com.example.socialize20.databinding.FragmentReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsFragment : Fragment() {
    lateinit var adapter: ReelFragAdapter
    var reelList= ArrayList<Reels>()
    private lateinit var binding: FragmentReelsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelsBinding.inflate(inflater, container, false)
        adapter = ReelFragAdapter(requireContext(),reelList)
//        setting the video through adapter on viewPager2
        binding.viewPager.adapter = adapter
//        Now collecting the reels from fireBase and showing over videoView
        Firebase.firestore.collection(REEl).get().addOnSuccessListener {
            var tempList = ArrayList<Reels>()
            reelList.clear()

            for (i in it.documents){
                var reels = i.toObject<Reels>()!!
                tempList.add(reels)
            }
            reelList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {
    }
}