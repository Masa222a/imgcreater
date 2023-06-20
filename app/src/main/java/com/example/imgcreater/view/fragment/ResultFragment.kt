package com.example.imgcreater.view.fragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.imgcreater.R
import com.example.imgcreater.databinding.FragmentResultBinding
import com.example.imgcreater.viewmodel.ResultViewModel
import com.squareup.picasso.Picasso

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private val args: MainFragmentArgs by navArgs()
    private val viewModel: ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        binding.apply {
            Picasso.get().load(args.imageEntity.ImageUrl).into(resultImg)

            backButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_resultFragment_to_nav_main)
            )

            val contentResolver = requireContext().contentResolver
            saveButton.setOnClickListener {
                val image = (resultImg.drawable as BitmapDrawable).bitmap
                viewModel.saveImageToStrage(image, contentResolver)
            }

            wordHistory.text = args.imageEntity.word
        }

        return binding.root
    }
}