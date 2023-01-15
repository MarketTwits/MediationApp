package com.example.mediationapp.presentor.screens.listen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mediationapp.R
import com.example.mediationapp.databinding.FragmentListenBinding
import com.example.mediationapp.presentor.adapters.time_adapter.TimeAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog


// TODO: Rename parameter arguments, choose names that match

class ListenFragment : Fragment() {

    private lateinit var binding : FragmentListenBinding
    private lateinit var dialog : BottomSheetDialog
    private lateinit var timeRecyclerView : RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentListenBinding.inflate(inflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.tvTime.setOnClickListener {
           showBottomSheet()
       }
       binding.imTimeIcon.setOnClickListener {
            showBottomSheet()
       }
    }
   private  fun showBottomSheet(){
        //tv_time and timeIcon make click
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_dialog_time, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)

        val timeAdapter = TimeAdapter()
        timeRecyclerView = dialogView.findViewById(R.id.rv_time_bottom_sheet)
        timeRecyclerView.adapter = timeAdapter
        dialog.show()

        timeAdapter.onSelectedTimeClickListener = {
            binding.tvTime.text = it
            dialog.dismiss()
        }



    }
}