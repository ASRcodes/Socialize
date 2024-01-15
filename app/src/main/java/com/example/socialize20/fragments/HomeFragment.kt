package com.example.socialize20.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialize20.Models.Post
import com.example.socialize20.Models.User
import com.example.socialize20.R
import com.example.socialize20.Utils.FOLLOW
import com.example.socialize20.Utils.POST
import com.example.socialize20.adapters.FollowAdapter
import com.example.socialize20.adapters.HomePostAdapter
import com.example.socialize20.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class HomeFragment : Fragment() {
//    variables for post activity
    private val postList = ArrayList<Post>()
    private lateinit var adapter: HomePostAdapter

    private val followList = ArrayList<User>()
    private lateinit var followAdapter: FollowAdapter
    lateinit var binding:FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
//        setting-up the toolbar over Home_Activity
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar)
//        Initializing the adapter
        adapter = HomePostAdapter(requireContext(),postList)
        binding.homRv.layoutManager=LinearLayoutManager(requireContext())
        binding.homRv.adapter=adapter

//        initializing follow adapter
        followAdapter = FollowAdapter(requireContext(),followList)
        binding.followRv.layoutManager =LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.followRv.adapter=followAdapter
//        Now we'll fetch the data of the current user's follow field and show them on recyclerView

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            followList.clear()

            for (i in it.documents){
                var user:User = i.toObject<User>()!!
                tempList.add(user)
            }
            followList.addAll(tempList)
            followAdapter.notifyDataSetChanged()
        }


//        Fetch the data from firebase and store them in a arrayList then return them over recyclerVIew
        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList = ArrayList<Post>()
            postList.clear()
            for (i in it.documents){
                var post:Post = i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_for_homefrag,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.like ->{
                Toast.makeText(requireContext(), "Work pending for this", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.message ->{
                Toast.makeText(requireContext(), "Work pending for this", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}