package com.example.socialize20.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.socialize20.R
import com.example.socialize20.databinding.FragmentAddBinding
import com.example.socialize20.post.PostActivity
import com.example.socialize20.post.ReelActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.AddPost.setOnClickListener{
            activity?.startActivity(Intent(requireContext(),PostActivity::class.java))
            activity?.finish()
        }
        binding.addPost.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),PostActivity::class.java))
            activity?.finish()
        }
        binding.Addreels.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),ReelActivity::class.java))
            activity?.finish()
        }
        binding.addReels.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),ReelActivity::class.java))
            activity?.finish()
        }
        return binding.root
    }

    companion object {

    }

}