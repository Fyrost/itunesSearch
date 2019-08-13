package com.example.myapplication.ui


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
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
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(R.navigation.browse,R.navigation.favorite)

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        controller.value?.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.browseFragment -> showBottomNav()
                R.id.favoriteFragment -> showBottomNav()
                R.id.descriptionFragment -> hideBottomNav()
            }
        }

        currentNavController = controller
    }

    override fun onNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun hideBottomNav() {
        bottom_nav.visibility = View.GONE
    }

    private fun showBottomNav() {
        bottom_nav.visibility = View.VISIBLE
    }
}
