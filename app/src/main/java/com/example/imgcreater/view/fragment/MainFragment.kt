package com.example.imgcreater.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.imgcreater.R
import com.example.imgcreater.databinding.FragmentMainBinding
import com.example.imgcreater.model.ImageEntity
import com.example.imgcreater.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime

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
                Timber.d("${binding.searchView.query}")
                if (searchView.query.isNotEmpty()) {
                    binding.progressBar.visibility = ProgressBar.VISIBLE
                    binding.loadingText.visibility = View.VISIBLE
                    binding.generateButton.visibility = View.INVISIBLE
                    setUpObserve()
                    viewModel.getData(searchView.query.toString())
                } else {
                    Toast.makeText(requireActivity(), "文字を入力してください", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun setUpObserve() {
        viewModel.imageUrl.observe(viewLifecycleOwner) { imageUrl ->
            if (imageUrl != null) {
                viewModel.saveToStorage(requireActivity().applicationContext, imageUrl)
            } else {
                binding.progressBar.visibility = ProgressBar.INVISIBLE
                binding.loadingText.visibility = View.INVISIBLE
                binding.generateButton.visibility = View.VISIBLE
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog
                    .setMessage(getString(R.string.generate_faild_message))
                    .setPositiveButton("はい") { _, _ ->
                        alertDialog.setOnDismissListener {
                            it.dismiss()
                        }
                    }.show()
            }
        }

        viewModel.uri.observe(viewLifecycleOwner) {
            val data = ImageEntity(
                0,
                it.toString(),
                binding.searchView.query.toString()
            )

            viewModel.insertImage(data)
            binding.progressBar.visibility = ProgressBar.INVISIBLE
            binding.loadingText.visibility = View.INVISIBLE

            val action = MainFragmentDirections.actionNavMainToResultFragment(data)
            findNavController().navigate(action)
        }
    }
}