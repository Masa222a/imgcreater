package com.example.imgcreater.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.imgcreater.R
import com.example.imgcreater.databinding.FragmentMainBinding
import com.example.imgcreater.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)


        binding.apply {
            searchView.setBackgroundColor(resources.getColor(android.R.color.transparent))

            generateButton.setOnClickListener {
                if (searchView.query.isNotEmpty()) {
                    viewModel.getData(searchView.query.toString())
                    Log.d("MainFragmentWord", "${searchView.query}")
                    findNavController().navigate(R.id.action_nav_main_to_resultFragment)
                } else {
                    Toast.makeText(requireActivity(), "文字を入力してください", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

}