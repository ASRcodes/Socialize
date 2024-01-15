package com.example.socialize20.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.socialize20.LoginScreen
import com.example.socialize20.Models.User
import com.example.socialize20.R
import com.example.socialize20.Utils.USER_NODE
import com.example.socialize20.adapters.viewPageAdapter
import com.example.socialize20.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var viewPageAdapter: viewPageAdapter
    lateinit var binding:FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    companion object{
    }

    override fun onStart() {
        super.onStart()
//        Getting the current user's information from firebase
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User = it.toObject<User>()!!
                binding.name.text = user.name
                binding.bio.text = user.email

//                here we have checked if user filed have image or not if not then do nothing and if found
//                import the picasso library and it will fetch the image from user field
                if (!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.profileIMG)
                }
            }
//        Now we'll do work for edit button i.e, on it's click we'll
//        move the same Login screen but this time with edit button
        binding.EditProfile.setOnClickListener{
//            Here we'll do intent passing also hence to tell that on it's click user is coming with edit req
            val  intent = Intent(activity,LoginScreen::class.java)
            intent.putExtra("MODE",1)
            activity?.startActivity(intent)
            activity?.finish()
        }
//        Setting up the values over viewPager
        viewPageAdapter = viewPageAdapter(requireActivity().supportFragmentManager)
        viewPageAdapter.addFragment(myReelsFragment(),"My reels")
        viewPageAdapter.addFragment(mypostFragment(),"My post")

        binding.viewpager.adapter = viewPageAdapter
        binding.tablayout.setupWithViewPager(binding.viewpager)
    }
}