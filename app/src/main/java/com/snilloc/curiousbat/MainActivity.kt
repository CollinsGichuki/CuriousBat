package com.snilloc.curiousbat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.snilloc.curiousbat.databinding.ActivityMainBinding
import com.snilloc.curiousbat.db.FaveActivityDatabase
import com.snilloc.curiousbat.repository.FaveActivityRepository
import com.snilloc.curiousbat.viewmodel.FavActivityViewModel
import com.snilloc.curiousbat.viewmodel.FaveActivityViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var favActivityViewModel: FavActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavBottom()

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val repository = FaveActivityRepository(FaveActivityDatabase.getDatabase(this))

        val viewModelFactory = FaveActivityViewModelFactory(repository)

        //Initialize the ViewModel
        favActivityViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavActivityViewModel::class.java)
    }

    private fun setUpNavBottom() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentHost) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(
            navController
        )

        val appConfigBar = AppBarConfiguration(setOf(R.id.homeFragment))
        setupActionBarWithNavController(navController, appConfigBar)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}