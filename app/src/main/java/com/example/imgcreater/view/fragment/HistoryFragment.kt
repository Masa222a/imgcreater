package com.example.imgcreater.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imgcreater.adapter.HistoryAdapter
import com.example.imgcreater.databinding.FragmentHistoryBinding
import com.example.imgcreater.model.ImageEntity
import com.example.imgcreater.viewmodel.HistoryViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this)[HistoryViewModel::class.java]
    }
    private var adapter: HistoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        adapter = HistoryAdapter(mutableListOf())
        recyclerView.adapter = adapter
        val layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.layoutManager = layoutManager

        viewModel.allImages.observe(viewLifecycleOwner) {
            changeImageList(it)
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    fun changeImageList(imageList: MutableList<ImageEntity>) {
        GlobalScope.launch(Dispatchers.Main) {
            adapter?.dataList = imageList
            adapter?.notifyDataSetChanged()
        }
    }

}