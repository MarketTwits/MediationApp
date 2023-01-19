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
import com.example.mediationapp.R
import com.example.mediationapp.presentor.adapters.block_adapter.BlockItemAdapter
import com.example.mediationapp.presentor.adapters.feelings_adapter.FeelingsItemAdapter
import com.example.mediationapp.databinding.FragmentMainBinding
import com.example.mediationapp.presentor.screens.welcome.EntryActivity
import com.example.mediationapp.data.firebase.FirebaseService
import com.example.mediationapp.domain.model.User
import com.example.mediationapp.presentor.screens.login.LoginFragment
import com.example.mediationapp.presentor.ui_events.fragmentToast
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

        updateUi()
        viewModel.getList()
        //loadDataViewModel()
        setupTypeRecyclerView()
        setupBlockRecyclerView()
    }

    private fun logOutUser() {
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
        viewModel.feelingsList.observe(viewLifecycleOwner) {
            feelingsAdapter = FeelingsItemAdapter()
            binding.rvUserseFeelengs.adapter = feelingsAdapter
            val horizontallyManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvUserseFeelengs.layoutManager = horizontallyManager

            //TODO
            feelingsAdapter.onFeelingsItemClickListener = {
                fragmentToast(it.title.toString())
            }
            feelingsAdapter.submitList(it)
        }
    }

    private fun setupBlockRecyclerView() {
        //BlockRV
        binding.progressBarBlock.isVisible = true
        viewModel.blockList.observe(viewLifecycleOwner) {
            blockAdapter = BlockItemAdapter()
            binding.rvBlocks.adapter = blockAdapter
            binding.rvBlocks.layoutManager = LinearLayoutManager(requireContext())
            blockAdapter.onBlockClickListener = {
                fragmentToast(it.title.toString())
            }
            blockAdapter.submitList(it)
            binding.progressBarBlock.isVisible = false
        }
    }

    private fun loadDataViewModel() {
        lifecycleScope.launch {
            viewModel.createFeelings()
            //viewModel.createBlocks()
        }
    }

    private fun updateUi() {
        //SetupListener
        binding.imMenuIcon.setOnClickListener {
            logOutUser()
        }
        loadUserInfoUI()
    }

    private fun loadUserInfoUI() {
        viewModel.getUserInfo()
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            validateUserInfo(it)
            val text = getString(R.string.greeting_user, it?.name)
            binding.tvWelcomeMain.text = text
            binding.tvWelcomeMain.isVisible = true
            binding.tvHowDoYouFeel.isVisible = true
        }
    }

    private fun validateUserInfo(user: User?) {
        if (user == null) {
            val intent = Intent(requireContext(), EntryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            fragmentToast(null, R.string.user_not_found)
        }

    }


}