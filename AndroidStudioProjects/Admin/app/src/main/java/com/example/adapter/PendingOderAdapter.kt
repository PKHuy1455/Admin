package com.example.adapter
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fastfoodapp.databinding.PendingOderItemBinding

class PendingOderAdapter(
    private val context:Context,
    private val customerNames: MutableList<String>,
    private val Quanity: MutableList<String>,
    private val foodImage: MutableList<String>,
//                         private val itemClicked: OnItemClicked,
) : RecyclerView.Adapter<PendingOderAdapter.PendingOderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOderViewHolder {
        val binding = PendingOderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOderViewHolder(binding)
    }



    override fun onBindViewHolder(holder: PendingOderViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int  =customerNames.size
    inner class PendingOderViewHolder(private val binding: PendingOderItemBinding):RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
           binding.apply {
               customerName.text = customerNames[position]
               Quanities.text = Quanity[position]
               var uriString:String  = foodImage[position]
               var uri = Uri.parse(uriString)
               Glide.with(context).load(uri).into(foodImageView)
               AcceptedBtn.apply {
                   if(!isAccepted){
                       text = "Accept"
                   }else{
                       text = "Dispatch"
                   }
                   setOnClickListener {
                      if(!isAccepted){
                          text = "Dispatch"
                          isAccepted = true
                          showToast("Oder is accepted")
                      }else{
                           customerNames.removeAt(adapterPosition)
                          notifyItemRemoved(adapterPosition)
                          showToast("Oder is dispatched")
                      }
                   }
               }

           }

        }
       private fun showToast(message:String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

    }
}