package com.example.socialize20.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialize20.Models.User
import com.example.socialize20.R
import com.example.socialize20.Utils.USER_NODE
import com.example.socialize20.adapters.SearchAdapter
import com.example.socialize20.databinding.FragmentSearchBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class SearchFragment : Fragment() {
    private lateinit var adapter: SearchAdapter
    private val userList = ArrayList<User>()
    lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = SearchAdapter(requireContext(),userList)
        binding.searchRvv.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRvv.adapter = adapter

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            userList.clear()

            for (i in it.documents){
                //            We don't need to show our id here so code to hide that
                if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){
                }
                else{
                    var user:User = i.toObject<User>()!!
                    tempList.add(user)
                }

            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        binding.search1.setOnClickListener{
//            First we'll fetch the data from the editText and store in  variable
            var text = binding.searchView.text.toString()
//            Now we'll match all the data in fireBase and give the output
            Firebase.firestore.collection(USER_NODE).whereEqualTo("name",text).get().addOnSuccessListener {
//                After finding the user we'll do the same work
                var tempList = ArrayList<User>()
                userList.clear()

                for (i in it.documents){
                    //            We don't need to show our id here so code to hide that
                    if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){
                    }
                    else{
                        var user:User = i.toObject<User>()!!
                        tempList.add(user)
                    }

                }
                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
        }







        return binding.root
    }

    companion object {

    }
}