package com.example.indiandefensewallpapers


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.indiandefensewallpapers.databinding.ItemImageBinding

//class ImageAdapter(private val imageList: List<ImageModel>) :
//    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ImageViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val imageModel = imageList[position]
//        holder.bind(imageModel)
//    }
//
//    override fun getItemCount(): Int {
//        return imageList.size
//    }
//
//    class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(imageModel: ImageModel) {
//            binding.imageView.setImageResource(imageModel.imageResId)
//        }
//    }
//}