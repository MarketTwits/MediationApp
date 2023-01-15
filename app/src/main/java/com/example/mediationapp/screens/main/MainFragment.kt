package com.example.mediationapp.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediationapp.adapters.block.BlockItemAdapter
import com.example.mediationapp.adapters.feelings.FeelingsItemAdapter
import com.example.mediationapp.databinding.FragmentMainBinding
import com.example.mediationapp.model.BlockElement
import com.example.mediationapp.model.FeelingsElement
import com.example.mediationapp.screens.welcome.EntryActivity
import com.example.mediationapp.servcie.FirebaseService
import kotlinx.coroutines.launch
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class MainFragment : Fragment() {
    lateinit var binding : FragmentMainBinding
    lateinit var viewModel: MainViewModel
    private lateinit var blockAdapter: BlockItemAdapter
    private lateinit var feelingsAdapter: FeelingsItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imMenuIcon.setOnClickListener {
            logOutUser()
        }
        viewModel.getList()
        //loadDataViewModel()
        setupTypeRecyclerView()
        setupBlockRecyclerView()
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

    private fun setupTypeRecyclerView() {

        //BlockFeeleings
        viewModel.feelingsList.observe(viewLifecycleOwner){
            feelingsAdapter = FeelingsItemAdapter()
            binding.rvUserseFeelengs.adapter = feelingsAdapter
            val horizontallyManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvUserseFeelengs.layoutManager = horizontallyManager

            //TODO
            feelingsAdapter.onFeelingsItemClickListener = {
                Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
            }
            feelingsAdapter.submitList(it)
        }
    }
    private fun setupBlockRecyclerView(){
        //BlockRV
        binding.progressBarBlocks.isVisible = true
        viewModel.blockList.observe(viewLifecycleOwner) {
            blockAdapter = BlockItemAdapter()
            binding.rvBlocks.adapter = blockAdapter
            binding.rvBlocks.layoutManager = LinearLayoutManager(requireContext())
            blockAdapter.onBlockClickListener = {
                Toast.makeText(requireContext(), it.title.toString(), Toast.LENGTH_SHORT).show()
            }
            blockAdapter.submitList(it)
            binding.progressBarBlocks.isVisible = false
        }
    }

    private fun loadDataViewModel(){
        lifecycleScope.launch {
            viewModel.createFeelings()
            //viewModel.createBlocks()
        }
    }


}