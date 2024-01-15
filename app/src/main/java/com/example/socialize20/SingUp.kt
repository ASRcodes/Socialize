package com.example.socialize20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.socialize20.Models.User
import com.example.socialize20.databinding.ActivitySingUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SingUp : AppCompatActivity() {
    val binding by lazy {
        ActivitySingUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.Login.setOnClickListener{
            if (binding.emailLogin.editableText?.toString().equals("")or
                binding.passwordLogin.editableText?.toString().equals("")){
                Toast.makeText(this, "Fill the details first", Toast.LENGTH_SHORT).show()
        }
            else{
//                Getting the email and password entered by user
                var user = User(binding.emailLogin.editableText?.toString(),
                        binding.passwordLogin.editableText?.toString())

//                Now check if the email and the password user entered does really exist and do SignUp
                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                    .addOnCompleteListener {
//                        If it's matched successfully
                        if(it.isSuccessful){
                            startActivity(Intent(this@SingUp,HomeScreen::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this, it.exception?.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.goback.setOnClickListener{
            startActivity(Intent(this@SingUp,LoginScreen::class.java))
            finish()
        }

    }
}