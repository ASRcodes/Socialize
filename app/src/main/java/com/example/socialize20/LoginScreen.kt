package com.example.socialize20

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.socialize20.Utils.USER_NODE
import com.example.socialize20.Models.User
import com.example.socialize20.Utils.USER_NODE_PROFILE
import com.example.socialize20.Utils.uploadImage
import com.example.socialize20.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class LoginScreen : AppCompatActivity() {
    val binding by lazy {
        ActivityLoginScreenBinding.inflate(layoutInflater)
    }
//    Variable for gallery launch
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri ->
        uri?.let{
            uploadImage(uri, USER_NODE_PROFILE){
                if (it!=null){
                    user.image =it
                    binding.profileIMG.setImageURI(uri)
                }
            }
        }
}
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        create a blank constructor also
        user = User()
        //    Here we'll accept the intent and do further changes
        if (intent.hasExtra("MODE")){
//            Here checking for the key
            if (intent.getIntExtra("MODE",-1)==1){
//                Now if it's Confirm then we'll do some changes
                binding.register.text = "Update profile"
                binding.already.text = "Right now you can only update profile image"
                binding.already.setTextColor(ContextCompat.getColor(this,R.color.already))
               binding.register.setBackgroundColor(ContextCompat.getColor(this,R.color.update))
                binding.loginhoga.text = ""
//                Setting the user's data
                com.google.firebase.Firebase.firestore.collection(USER_NODE).document(com.google.firebase.Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!
//                        right now we'll update image only
//                        so first get the old image
                        if (!user.image.isNullOrEmpty()){
                            Picasso.get().load(user.image).into(binding.profileIMG)
//                      Setting the current user's name email and further details
                            binding.name.setText(user.name)
                            binding.email.setText(user.email)
                            binding.password.setText(user.password)
                        }
                    }
            }
        }





        binding.register.setOnClickListener {
//            Now here if the MODE ==1 then we have to do something else
            if (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE", -1) == 1) {
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                    Toast.makeText(this, "Update completed", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginScreen,HomeScreen::class.java))
                    finish()
                }

            } else {

                if (binding.name.editableText?.toString().equals("") or
                    binding.email.editableText?.toString().equals("") or
                    binding.password.editableText?.toString().equals("")
                ) {
                    Toast.makeText(this, "Enter the details first", Toast.LENGTH_SHORT).show()
                } else {
//                Here we'll push the enter the given email,password and name to firebase
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.email.editableText.toString(),
                        binding.password.editableText.toString()
                    ).addOnCompleteListener {
//                    here we'll check if the work is done or not
                            result ->
                        if (result.isSuccessful) {
//                        Toast.makeText(this, "Informations added successfully on firebase", Toast.LENGTH_SHORT).show()
                            user.name = binding.name.editableText?.toString()
                            user.email = binding.email.editableText?.toString()
                            user.password = binding.password.editableText?.toString()
                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                            Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginScreen, HomeScreen::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                result.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        binding.addImage.setOnClickListener{
            launcher.launch("image/*")
        }
        binding.loginhoga.setOnClickListener {
            startActivity(Intent(this@LoginScreen,SingUp::class.java))
            finish()
        }
    }
}