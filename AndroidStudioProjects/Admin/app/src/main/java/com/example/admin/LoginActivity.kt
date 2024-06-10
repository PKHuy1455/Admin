package com.example.fastfoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.admin.main_Activity
import com.example.fastfoodapp.databinding.ActivityLoginBinding
import com.example.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var email: String
    private  var username: String? =null
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference



        binding.LoginBtn.setOnClickListener {
            // Lấy dữ liệu từ textfield
            email = binding.EmailTxt.text.toString().trim()
            password = binding.Passwordtxt.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Bạn chưa nhập email hoặc mật khẩu", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }
        }

        binding.donthaveacc.setOnClickListener {
            val intent = Intent(this, SignActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user: FirebaseUser? = auth.currentUser
                Toast.makeText(this, "Login succesfully", Toast.LENGTH_SHORT).show()
                if (user != null) {
                    updateUi(user)
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user: FirebaseUser? = auth.currentUser
                            Toast.makeText(this, "Create user & Login succesfully", Toast.LENGTH_SHORT).show()
                            saveUserData()
                            if (user != null) {
                                updateUi(user)
                            }
                        } else {
                            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                            Log.d("Account", "createUserAccount: Authentication failed", task.exception)
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "signInWithEmailAndPassword: Authentication failed", task.exception)
            }
        }
    }

    private fun saveUserData() {

        email = binding.EmailTxt.text.toString().trim()
        password = binding.Passwordtxt.text.toString().trim()
        val user = UserModel(username,email, password)
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        userID?.let {
            database.child("user").child(it).setValue(user)
        }
    }

    private fun updateUi(user: FirebaseUser) {
        startActivity(Intent(this, main_Activity::class.java))
        finish()
    }
}


