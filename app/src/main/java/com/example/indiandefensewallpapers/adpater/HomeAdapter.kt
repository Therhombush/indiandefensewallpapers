package com.example.indiandefensewallpapers.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.indiandefensewallpapers.Model.homeModel
import com.example.indiandefensewallpapers.R
import java.util.ArrayList

class HomeAdapter(val requireContext: Context , val listhome: ArrayList<homeModel>):
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
       val imageview=itemView.findViewById<ImageView>(R.id.bom_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_image,parent,false)
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        Glide.with(requireContext).load(listhome[position].link).into(holder.imageview);
    }

    override fun getItemCount()=listhome.size
}