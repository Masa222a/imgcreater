package com.example.imgcreater.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.imgcreater.R
import com.example.imgcreater.databinding.ActivityMainBinding
import com.example.imgcreater.view.fragment.HistoryFragment
import com.example.imgcreater.view.fragment.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav = binding.bottomNavigation
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main -> {
                    loadFragment(MainFragment())
                    true
                }
                else -> {
                    loadFragment(HistoryFragment())
                    true
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.container, fragment)
            commit()
        }

    }
}