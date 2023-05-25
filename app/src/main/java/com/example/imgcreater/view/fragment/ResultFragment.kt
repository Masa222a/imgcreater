package com.example.imgcreater.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.imgcreater.R
import com.example.imgcreater.databinding.FragmentResultBinding
import com.squareup.picasso.Picasso

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private val args: MainFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        Picasso.get().load(args.generateData.ImageUrl).into(binding.resultImg)

        binding.backButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_resultFragment_to_nav_main)
        )

        return binding.root
    }

}