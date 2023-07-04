package com.example.imgcreater.view.fragment

import android.app.AlertDialog
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
import timber.log.Timber

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
                    progressBar.visibility = ProgressBar.VISIBLE
                    loadingText.visibility = View.VISIBLE
                    generateButton.visibility = View.INVISIBLE

                    viewModel.getData(searchView.query.toString())
                } else {
                    Toast.makeText(requireActivity(), getString(R.string.please_enter_a_letter), Toast.LENGTH_SHORT).show()
                }
            }
        }

        setUpObserve()

        return binding.root
    }

    private fun setUpObserve() {
        viewModel.imageUrl.observe(viewLifecycleOwner) { imageUrl ->
            if (imageUrl != null) {
                viewModel.saveToStorage(requireActivity().applicationContext, imageUrl)
            } else {
                startAlert()
            }
        }

        viewModel.uri.observe(viewLifecycleOwner) {
            val data = ImageEntity(
                0,
                it.toString(),
                binding.searchView.query.toString()
            )

            viewModel.insertImage(data)
            binding.apply {
                progressBar.visibility = ProgressBar.INVISIBLE
                loadingText.visibility = View.INVISIBLE
                searchView.setQuery("", false)
                searchView.isIconified = true
            }

            val action = MainFragmentDirections.actionNavMainToResultFragment(data)
            findNavController().navigate(action)
        }
    }

    private fun startAlert() {
        binding.apply {
            progressBar.visibility = ProgressBar.INVISIBLE
            loadingText.visibility = View.INVISIBLE
            generateButton.visibility = View.VISIBLE
        }
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog
            .setMessage(getString(R.string.generate_faild_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                alertDialog.setOnDismissListener {
                    it.dismiss()
                }
            }.show()
    }
}