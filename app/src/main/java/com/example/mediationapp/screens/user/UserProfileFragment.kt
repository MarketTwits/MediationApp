package com.example.mediationapp.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

import com.example.mediationapp.adapters.TypeMediationAdapter
import com.example.mediationapp.databinding.FragmentUserProfileBinding
import com.example.mediationapp.model.MeditationElement
import java.text.SimpleDateFormat
import java.util.*


class UserProfileFragment : Fragment() {
    lateinit var binding: FragmentUserProfileBinding
    private lateinit var adapter: TypeMediationAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater,container, false)

        setupTypeRecyclerView()


        return binding.root
    }

        private fun setupTypeRecyclerView(){
    adapter = TypeMediationAdapter()
    binding.rvUsersMood.adapter = adapter
    val layoutManager = GridLayoutManager(context, 2)
    binding.rvUsersMood.layoutManager = layoutManager



    adapter.addItem(addItem())
    }
    private fun addItem() : MeditationElement {
        val id = Random().nextInt()
        val sdf = SimpleDateFormat("hh:mm")
            val currentDate = sdf.format(Date())
            val newItem = MeditationElement(id, "https://example.com/image.jpg", currentDate)
            return newItem
    }


}