package com.yunuscagliyan.soccerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.yunuscagliyan.soccerapp.databinding.ActivityMainBinding
import com.yunuscagliyan.soccerapp.utils.ThemeHelper
import com.yunuscagliyan.soccerapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding


    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupTheme()
        setContentView(binding.root)

        //init navController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupTheme() {
        mainViewModel.screenModePreference.observe(this) { screenMode ->
            if (screenMode.isNightMode) {
                ThemeHelper.applyTheme(ThemeHelper.DARK_MODE)
            } else {
                ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE)
            }

        }
    }

    fun setUpToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()||super.onSupportNavigateUp()
    }
}