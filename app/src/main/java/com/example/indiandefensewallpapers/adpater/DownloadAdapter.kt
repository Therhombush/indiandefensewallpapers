package com.example.indiandefensewallpapers.adpater

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.indiandefensewallpapers.FinalWallpaper
import com.example.indiandefensewallpapers.ImageModel
import com.example.indiandefensewallpapers.R

data class ImageModel(val name: String, val uri: Uri)

class DownloadAdapter(private val context: Context, private val images: MutableList<ImageModel>) :
    RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder>() {

    inner class DownloadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.bom_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return DownloadViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
        val image = images[position]
        Glide.with(context).load(image.uri).into(holder.imageView)

        holder.itemView.setOnClickListener {
            // Launch FinalWallpaper activity to view the image fullscreen or set it as wallpaper
            val intent = Intent(context, FinalWallpaper::class.java)
            intent.putExtra("imageUri", image.uri.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = images.size
}
