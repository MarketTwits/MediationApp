package com.example.mediationapp.presentor.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mediationapp.R
import com.example.mediationapp.presentor.adapters.block_adapter.BlockItemAdapter
import com.example.mediationapp.presentor.adapters.feelings_adapter.FeelingsItemAdapter
import com.example.mediationapp.databinding.FragmentMainBinding
import com.example.mediationapp.presentor.screens.welcome.EntryActivity
import com.example.mediationapp.domain.model.User
import com.example.mediationapp.presentor.screens.login.LoginFragment
import com.example.mediationapp.presentor.ui_events.LoadingFinish
import com.example.mediationapp.presentor.ui_events.LoadingProgress
import com.example.mediationapp.presentor.ui_events.fragmentToast
import com.example.mediationapp.presentor.ui_events.logOutUser
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel
    private lateinit var blockAdapter: BlockItemAdapter
    private lateinit var feelingsAdapter: FeelingsItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        notifyLoadingState()
        viewModel.getList()
        loadUserInfoUI()
        setupTypeRecyclerView()
        setupBlockRecyclerView()
    }

    private fun setupTypeRecyclerView() {

        //BlockFeeleings
        feelingsAdapter = FeelingsItemAdapter()
        binding.rvUserseFeelengs.adapter = feelingsAdapter
        val horizontallyManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvUserseFeelengs.layoutManager = horizontallyManager
        viewModel.feelingsList.observe(viewLifecycleOwner) {
            feelingsAdapter.submitList(it)
            feelingsAdapter.onFeelingsItemClickListener = {
                fragmentToast(it.title.toString())
            }
        }
    }

    private fun setupBlockRecyclerView() {
        //BlockRV
        blockAdapter = BlockItemAdapter()
        binding.rvBlocks.adapter = blockAdapter
        binding.rvBlocks.layoutManager = LinearLayoutManager(requireContext())
        viewModel.blockList.observe(viewLifecycleOwner) { blockList ->
            blockAdapter.submitList(blockList)
            blockAdapter.onBlockClickListener = {
                fragmentToast(it.title.toString())
            }

        }
    }

    private fun loadDataViewModel() {
        lifecycleScope.launch {
            viewModel.createFeelings()
            //viewModel.createBlocks()
        }
    }

    private fun loadUserInfoUI() {
        viewModel.getUserInfo()
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            validateUserInfo(it)
            val text = getString(R.string.greeting_user, it?.name)
            Glide
                .with(requireContext())
                .load(it?.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.imProfileImageMain)

            binding.tvWelcomeMain.text = text
        }
    }
    private fun notifyLoadingState(){
        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it) {
                is LoadingProgress -> {
                    binding.progressBarBlock.visibility = View.VISIBLE
                }
                is LoadingFinish -> {
                    binding.progressBarBlock.visibility = View.GONE
                    binding.tvWelcomeMain.isVisible = true
                    binding.tvHowDoYouFeel.isVisible = true
                }
                else -> {
                    binding.progressBarBlock.visibility = View.GONE
                }
            }
        }
    }

    private fun validateUserInfo(user: User?) {
        if (user == null) {
            logOutUser()
            fragmentToast(null, R.string.user_not_found)
        }

    }
}