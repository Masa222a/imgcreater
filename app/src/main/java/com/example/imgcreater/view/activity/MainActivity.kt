package com.example.imgcreater.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.imgcreater.R
import com.example.imgcreater.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHost) as NavHostFragment? ?: return

        val navController = host.navController

        setupBottomNavMenu(navController)

    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)
    }

}