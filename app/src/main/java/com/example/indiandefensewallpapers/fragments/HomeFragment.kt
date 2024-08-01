package com.example.indiandefensewallpapers.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
//import com.example.indiandefensewallpapers.ImageAdapter
import com.example.indiandefensewallpapers.Model.homeModel
import com.example.indiandefensewallpapers.adpater.HomeAdapter
import com.example.indiandefensewallpapers.databinding.ActivityMainBinding
import com.example.indiandefensewallpapers.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    //lateinit var imageAdapter: ImageAdapter
    lateinit var db:FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        db= FirebaseFirestore.getInstance()
        db.collection("/home").addSnapshotListener { value, error ->
            val  listhome= arrayListOf<homeModel>()
            val data=value?.toObjects(homeModel::class.java)
            listhome.addAll(data!!)

            binding.rcvHome.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.rcvHome.adapter = HomeAdapter(requireContext(), listhome)

        }
        return binding.root


    }
}