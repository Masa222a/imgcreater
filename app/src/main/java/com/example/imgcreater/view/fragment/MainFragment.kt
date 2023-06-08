package com.example.imgcreater.view.fragment

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
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
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
                if (searchView.query.isNotEmpty()) {
                    viewModel.getData(searchView.query.toString())
                    setUpObserve()
                } else {
                    Toast.makeText(requireActivity(), "文字を入力してください", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    fun setUpObserve() {
        viewModel.imageUrl.observe(viewLifecycleOwner) {
            GlobalScope.launch(Dispatchers.IO) {
                val bitmap: Bitmap = Picasso.get().load(it).get()
                val directory = ContextWrapper(context).getDir(
                    "image",
                    Context.MODE_PRIVATE
                )
                val localDateTime = LocalDateTime.now()
                val file = File(directory, "$localDateTime")
                FileOutputStream(file).use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    viewModel.uri.postValue(viewModel.getImageUri(requireContext().applicationContext, bitmap))
                }
            }

            viewModel.uri.observe(viewLifecycleOwner) {
                val data = ImageEntity(
                    0,
                    it.toString(),
                    binding.searchView.query.toString()
                )

                viewModel.insertImage(data)

                val action = MainFragmentDirections.actionNavMainToResultFragment(data)
                findNavController().navigate(action)
            }
            Log.d("MainFragmentWord", "${binding.searchView.query}")
        }
    }
}