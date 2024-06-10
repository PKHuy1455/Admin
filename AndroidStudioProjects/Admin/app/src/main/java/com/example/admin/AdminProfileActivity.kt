package com.example.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastfoodapp.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.BackBtn.setOnClickListener {
            finish()
        }
        binding.Name.isEnabled = false
        binding.Address.isEnabled = false
        binding.Email.isEnabled = false
        binding.Phonenum.isEnabled = false
        binding.Password.isEnabled = false

         var isEnable = false
        binding.editBtn.setOnClickListener {
            isEnable = !isEnable
            binding.Name.isEnabled = isEnable
            binding.Address.isEnabled = isEnable
            binding.Email.isEnabled = isEnable
            binding.Phonenum.isEnabled = isEnable
            binding.Password.isEnabled = isEnable
            if(isEnable){
                binding.Name.requestFocus()
            }
        }



    }

}