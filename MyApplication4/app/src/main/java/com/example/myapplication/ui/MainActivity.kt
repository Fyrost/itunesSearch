package com.example.myapplication.ui


import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController

import com.example.myapplication.R

import kotlinx.android.synthetic.main.activity_main.*

import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein


class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()

    private lateinit var navController: NavController

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {
        setSupportActionBar(toolbar)
        navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this@MainActivity, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.browseFragment -> showBottomNav()
                R.id.favoriteFragment -> showBottomNav()
                R.id.descriptionFragment -> hideBottomNav()
            }
        }
    }

    private fun hideBottomNav() {
        bottom_nav.visibility = View.GONE
    }

    private fun showBottomNav() {
        bottom_nav.visibility = View.VISIBLE
    }
}
