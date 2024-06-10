package com.example.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.DeliveryAdapter
import com.example.fastfoodapp.R
import com.example.fastfoodapp.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding:ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.BackBtn.setOnClickListener {
            finish()
        }
       val customerName = arrayListOf(
           "Khắc Huy",
           "Nhật Minh",
           "Văn Thành"
       )
        val moneyStatus = arrayListOf(
            "Received",
            "Not Received",
            "Pending"
        )
        val adapter = DeliveryAdapter(customerName, moneyStatus)
        binding.DeliveryRecyclerView.adapter = adapter
        binding.DeliveryRecyclerView.layoutManager = LinearLayoutManager(this,)
    }
}