package com.example.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.PendingOderAdapter
import com.example.fastfoodapp.databinding.ActivityPendingOderBinding
import com.example.model.OderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOderActivity : AppCompatActivity() {
    private val binding: ActivityPendingOderBinding = TODO()
    private var listOfName:MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfImageFirstFoodOrder:MutableList<String> = mutableListOf()
    private var listOfOderItem:MutableList<OderDetails> = mutableListOf()
    private lateinit var database:FirebaseDatabase
    private lateinit var databaseOderDetails: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        databaseOderDetails = database.reference.child("OrderDetails")
        getOrderDetails()

        binding.BackBtn.setOnClickListener {
            finish()
        }


    }

    private fun getOrderDetails() {
        databaseOderDetails.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ordersnapshot:DataSnapshot in snapshot.children ){
                    val orderDetails: OderDetails? = ordersnapshot.getValue(OderDetails::class.java)
                    orderDetails?.let {
                        listOfOderItem.add(it)
                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun addDataToListForRecyclerView() {
        for(orderItem:OderDetails in listOfOderItem ){
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImages?.filterNot { it.isEmpty() }?.forEach {
                listOfImageFirstFoodOrder.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingoderRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOderAdapter(this,listOfName,listOfTotalPrice,listOfImageFirstFoodOrder)
        binding.pendingoderRecyclerView.adapter = adapter
    }

}