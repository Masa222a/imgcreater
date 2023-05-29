package com.example.imgcreater.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.imgcreater.databinding.FragmentMainBinding
import com.example.imgcreater.model.ImageEntity
import com.example.imgcreater.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
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
                    viewModel.imageUrl.observe(viewLifecycleOwner) {
                        val data = ImageEntity(
                            0,
                            it,
                            searchView.query.toString()
                        )

                        viewModel.insertImage(data)

                        val action = MainFragmentDirections.actionNavMainToResultFragment(data)
                        findNavController().navigate(action)
                        Log.d("MainFragmentWord", "${searchView.query}")
                    }
                } else {
                    Toast.makeText(requireActivity(), "文字を入力してください", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

}