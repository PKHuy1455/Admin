package com.example.fastfoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.fastfoodapp.databinding.ActivitySignBinding
import com.example.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var database: DatabaseReference
    private val binding: ActivitySignBinding by lazy {
        ActivitySignBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        binding.logButton.setOnClickListener {
            username = binding.Nametxt.text.toString().trim()
            email = binding.EmailTxt.text.toString().trim()
            password = binding.Passwordtxt.text.toString().trim()

            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }
        }

        binding.alreadyhaveacc.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Account creation failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }
    }
//Lưu dữ liệu vào database
    private fun saveUserData() {
        username = binding.Nametxt.text.toString().trim()
        email = binding.EmailTxt.text.toString().trim()
        password = binding.Passwordtxt.text.toString().trim()
        val user = UserModel(username,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
    //save user data Firebase database
        database.child("user").child(userId).setValue(user)
    }
}
