package com.example.socialize20.post

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.socialize20.HomeScreen
import com.example.socialize20.Models.Reels
import com.example.socialize20.Models.User
import com.example.socialize20.Utils.REEL_FOLDER
import com.example.socialize20.Utils.REEl
import com.example.socialize20.Utils.USER_NODE
import com.example.socialize20.Utils.uploadVideo
import com.example.socialize20.databinding.ActivityReelBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityReelBinding.inflate(layoutInflater)
    }
    //    variable to store Video url for post
    private lateinit var videoUrl:String
//    variable for progressDialog
    lateinit var progressDialog: ProgressDialog
    //        Variable for gallery launch
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri ->
        uri?.let{
            uploadVideo(uri, REEL_FOLDER, progressDialog){
                    url->
                if (url != null) {
                    videoUrl = url
                }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog=ProgressDialog(this)
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener {
            startActivity(Intent(this@ReelActivity,HomeScreen::class.java))
        }
        binding.button.setOnClickListener {
            startActivity(Intent(this@ReelActivity,HomeScreen::class.java))
        }
        binding.addReelVdo.setOnClickListener {
            launcher.launch("video/*")
//            code for if user comes with no video selected
                binding.addReelVdo.text = "Video selected"
        }
//        Onn the click of post button image should be posted over our firebase
        binding.addPosT.setOnClickListener {
//            Fetching user's data to store is profile image
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user:User = it.toObject<User>()!!
                Toast.makeText(this, "Once video is posted you'll be moved to Home_Screen", Toast.LENGTH_SHORT).show()
//            set url
                var reels: Reels = Reels(videoUrl!!, binding.writeCaption.editableText.toString(),user.image!!)
                Firebase.firestore.collection(REEl).document().set(reels).addOnSuccessListener {
//                we'll create one more node of current user in which his post the current user's post will be saved
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEl).document().set(reels)
                        .addOnSuccessListener {
//                    after posting we'll move the user to the Home Activity
                            startActivity(Intent(this@ReelActivity, HomeScreen::class.java))
                            finish()
                        }
            }

            }
        }
    }
}