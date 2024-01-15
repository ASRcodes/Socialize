package com.example.socialize20.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.socialize20.HomeScreen
import com.example.socialize20.Models.Post
import com.example.socialize20.Models.User
import com.example.socialize20.Utils.POST
import com.example.socialize20.Utils.POST_FOLDER
import com.example.socialize20.Utils.USER_NODE
import com.example.socialize20.Utils.USER_NODE_PROFILE
import com.example.socialize20.Utils.uploadImage
import com.example.socialize20.databinding.ActivityPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
//    variable to store img url for post
    var imageurl:String? = null
//        Variable for gallery launch
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri ->
        uri?.let{
            uploadImage(uri, POST_FOLDER){
                    url->
            if (url!=null){
            binding.addpostImage.setImageURI(uri)
//                uploading the url we get in our imageurl
                imageurl = url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity,HomeScreen::class.java))
        }
        binding.button.setOnClickListener {
            startActivity(Intent(this@PostActivity,HomeScreen::class.java))

        }
        binding.addpostImage.setOnClickListener {
            launcher.launch("image/*")
        }
//        Onn the click of post button image should be posted over our firebase
        binding.addPosT.setOnClickListener {
//            here we'll fetch data from firebase and set it over the
            //            created variables and then show over the created layout for homeFrag
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
//                Code to get the user's folder
                var user = it.toObject<User>()!!
                var post: Post = Post(
                    posturl = imageurl!!,
                    postCaption = binding.writeCaption.editableText.toString(),
                    uid = user.name.toString(),
                    time = System.currentTimeMillis().toString()
                )
            }

//            set url
            var post:Post = Post(imageurl!!,binding.writeCaption.editableText.toString())
            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
//                we'll create one more node of current user in which his post the current user's post will be saved
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
//                    after posting we'll move the user to the Home Activity
                    startActivity(Intent(this@PostActivity,HomeScreen::class.java))
                    finish()
                }
            }
        }



    }
}