package com.obando.test.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.obando.test.R
import com.obando.test.core.base.BaseActivity
import com.obando.test.databinding.ActivityHostWelcomeBinding

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityHostWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHostWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }


    private fun initView() {
        setupBottomNavigation(
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
                .navController
        )
    }

    private fun setupBottomNavigation(navController: NavController) {
        with(binding) {
            dashboardBottomNavigationView.setupWithNavController(navController)
            dashboardBottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFragment -> {
                        navController.navigate(R.id.homeFragment)
                    }
                }
                true
            }
        }
    }
}
