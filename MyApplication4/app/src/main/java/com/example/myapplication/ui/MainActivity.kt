package com.example.myapplication.ui


import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.activity_main.*

import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein


class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()

    private var currentNavController: LiveData<NavController>? = null

    private lateinit var navController: NavController

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
//        setupNavigation()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(R.navigation.mobile_navigation)

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )


//        navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when(destination.id) {
//                R.id.browseFragment -> showBottomNav()
//                R.id.favoriteFragment -> showBottomNav()
//                R.id.descriptionFragment -> hideBottomNav()
//            }
//        }


        currentNavController = controller
    }

    override fun onNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

//    private fun setupNavigation() {
//        setSupportActionBar(toolbar)
//        navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
//        bottom_nav.setupWithNavController(navController)
//        NavigationUI.setupActionBarWithNavController(this@MainActivity, navController)
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when(destination.id) {
//                R.id.browseFragment -> showBottomNav()
//                R.id.favoriteFragment -> showBottomNav()
//                R.id.descriptionFragment -> hideBottomNav()
//            }
//        }
//    }

    private fun hideBottomNav() {
        bottom_nav.visibility = View.GONE
    }

    private fun showBottomNav() {
        bottom_nav.visibility = View.VISIBLE
    }
}
