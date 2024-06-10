package com.example.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fastfoodapp.databinding.ItemBinding
import com.example.model.AllMenu
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(

    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference

): RecyclerView.Adapter<MenuItemAdapter.AdditemViewHolder>() {

    private val itemQuanities = IntArray(menuList.size){1}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditemViewHolder {
        val binding  = ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdditemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdditemViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int  = menuList.size
    inner  class AdditemViewHolder(private val binding:ItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
          binding.apply {
              val quanity = itemQuanities[position]
              val menuItem:AllMenu = menuList[position]
              val uriString:String? = menuItem.foodImage
              val uri = Uri.parse(uriString)
            foodnameTxtView.text = menuItem.foodname
              foodpriceTxtView.text = menuItem.foodPrice
              Glide.with(context).load(uri).into(foodImageView)

              quanityTxtView.text = quanity.toString()
              minusBtn.setOnClickListener{
                  decreaseQuanity(position)
              }
              plusBtn.setOnClickListener{
                  increaseQuanity(position)
              }
              deletefoodBtn.setOnClickListener{
                  DeleteQuanity(position)
              }
          }
        }

        private fun decreaseQuanity(position: Int) {
            if(itemQuanities[position]>1){
                itemQuanities[position]--
                binding.quanityTxtView.text = itemQuanities[position].toString()
            }
        }
        private fun increaseQuanity(position: Int) {
            if(itemQuanities[position]<10){
                itemQuanities[position]++
                binding.quanityTxtView.text = itemQuanities[position].toString()
            }
        }
        private fun DeleteQuanity(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)
        }
        //có thể sai ở đây

    }
}