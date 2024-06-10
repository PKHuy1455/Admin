package com.example.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastfoodapp.R
import com.example.fastfoodapp.databinding.ActivityMainBinding

class main_Activity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.addmenu.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.AllItemMenu.setOnClickListener {
            val intent = Intent(this, All_ItemActivity::class.java)
            startActivity(intent)
        }
        binding.outForDeliveryBtn.setOnClickListener {
            val intent = Intent(this, OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.AdminProfileBtn.setOnClickListener {
            val intent = Intent(this, AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.CreateNewUserBtn.setOnClickListener {
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }
        binding.PendingOder.setOnClickListener {
            val intent = Intent(
                this,PendingOderActivity::class.java
            )
            startActivity(intent)
        }
    }
}