package com.example.indiandefensewallpapers

//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.indiandefensewallpapers.databinding.ActivityMainBinding
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class MainActivity : AppCompatActivity() {
//
//    lateinit var binding : ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        val dataset = arrayOf("January", "February", "March")
//        val customAdapter = ImagesRecyclerViewAdapter(dataset)
//
//        val recyclerView: RecyclerView = binding.imagesRecyclerView
//        recyclerView.layoutManager = GridLayoutManager(this,2)
//        recyclerView.adapter = customAdapter
//    }
//}

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.indiandefensewallpapers.databinding.ActivityMainBinding
import com.example.indiandefensewallpapers.fragments.DownloadFragment
import com.example.indiandefensewallpapers.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.icHome.setOnClickListener{
            replaceFragment(HomeFragment())
        }

        binding.icDownload.setOnClickListener{
            replaceFragment(DownloadFragment())
        }

////        val imageList = listOf(
////            ImageModel(R.drawable.screenshot_20240716_154923),
////            ImageModel(R.drawable.screenshot_20240716_155009),
////            ImageModel(R.drawable.screenshot_20240716_155031),
////            ImageModel(R.drawable.screenshot_20240716_155047),
////            ImageModel(R.drawable.screenshot_20240716_155052),
////            ImageModel(R.drawable.screenshot_20240716_155102),
////
////
////            // Add more images as needed
////        )
//
//        imageAdapter = ImageAdapter(imageList)
//
//        binding.recyclerView.layoutManager = GridLayoutManager(this,3)
//        binding.recyclerView.adapter = imageAdapter
    }

    fun replaceFragment(fragment: Fragment)
    {
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragmentReplace, fragment)
            transition.commit()

    }
}