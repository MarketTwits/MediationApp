package com.example.mediationapp.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediationapp.adapters.BlockItemAdapter
import com.example.mediationapp.databinding.FragmentMainBinding
import com.example.mediationapp.model.BlockElement
import com.example.mediationapp.screens.welcome.EntryActivity
import com.example.mediationapp.servcie.FirebaseService
import java.util.*
import java.util.EnumSet.range


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class MainFragment : Fragment() {
    lateinit var binding : FragmentMainBinding
    private lateinit var adapter: BlockItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imMenuIcon.setOnClickListener {
            logOutUser()
        }
        setupTypeRecyclerView()
    }
    private fun logOutUser(){
        /*
        Logging out the user from the account.
        An intent flag is created in order not to add the previous activity to the backstack
             */
        FirebaseService.auth.signOut()
        //Open start activity
        val intent = Intent(requireContext(), EntryActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun setupTypeRecyclerView(){

        adapter = BlockItemAdapter()
        binding.rvBlocks.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvBlocks.layoutManager = layoutManager

        adapter.onBlockClickListener = {
            Toast.makeText(requireContext(), it.title.toString(), Toast.LENGTH_SHORT).show()
        }

        val item = createBlock()
        for (i in 1..15){
            adapter.addItem(item)
        }
    }
    private fun createBlock(): BlockElement {
        val id = Random().nextInt()

        return BlockElement(
            id,
            "Title",
            "Краткое описание блока двумя строчками",
            "mainText",
            "https://pcdn.columbian.com/wp-content/uploads/2021/06/0615_fea_meditation.jpg"
        )
    }


}