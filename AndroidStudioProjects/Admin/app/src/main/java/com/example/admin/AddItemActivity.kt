package com.example.admin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fastfoodapp.databinding.ActivityAddItemBinding
import com.example.model.AllMenu
import com.example.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class AddItemActivity : AppCompatActivity() {

    private lateinit var foodname:String
    private lateinit var foodPrice:String
    private lateinit var foodDecription:String
    private var foodImage: Uri? =null

    //firebase
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private  val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.AddBtn.setOnClickListener {
            //lấy dữ liệu từ textField
            foodname = binding.foodNametxt.text.toString().trim()
            foodPrice = binding.foodPricetxt.text.toString().trim()
            foodDecription = binding.description.text.toString().trim()

            if(!(foodname.isBlank() || foodDecription.isBlank() || foodPrice.isBlank())){
                uploadData()
                Toast.makeText(this, "Item Add Succesfully", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()
            }

        }
        binding.Selectimage.setOnClickListener {
            pickimage.launch("image/*")
        }


        binding.BackBtn.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {

        //get a reference to the "menu" node in the database
        val menuRef: DatabaseReference = database.getReference("menu")
        //Genrate a unique key for the new menu item
        val newItemKey: String? = menuRef.push().key
        if(foodImage!= null){
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef:StorageReference = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask: UploadTask = imageRef.putFile(foodImage!!)
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl->
                    //Create new item
                    val newItem = AllMenu(
                        foodname = foodname,
                        foodPrice = foodPrice,
                        foodDecription = foodDecription,
                        foodImage = downloadUrl.toString()
                    ) //Có thể bị lỗi chỗ này
                    println(newItem)
                    newItemKey?.let { key->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "Data uploadted succesfully", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener{
                                Toast.makeText(this, "Data uploadted failed", Toast.LENGTH_SHORT).show()
                            }
                    }

                }

            } .addOnFailureListener {
                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please seclect an image", Toast.LENGTH_SHORT).show()
        }

    }

    private val pickimage = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        if(uri != null){
            binding.Selectimage2.setImageURI(uri)
            foodImage = uri
        }
    }
}