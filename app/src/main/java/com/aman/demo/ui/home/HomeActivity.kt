package com.aman.demo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.aman.demo.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Set the Toolbar for HOme activity
        //toolbar_id is the id defined in activity_home
        setSupportActionBar(toolbar_id)

        //Get the Navigation Controller
        //2nd param is id of NavHostFragment
        val navController = Navigation.findNavController(this, R.id.navHostFragment)

        //Set up the Navigation UI
        //1st param is navigation VIew
        NavigationUI.setupWithNavController(navigationViewForHomeDrawerLayout, navController)

        //Set up the action bar with the Nav Controller so that the fragment title is changed automatically
        NavigationUI.setupActionBarWithNavController(
            this,
            navController,
            home_navigation_drawer_layout_id
        )

    }

    //To Support Back Functionality When user clicks on Back Arrow button User should come back to back screen
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.navHostFragment),
            home_navigation_drawer_layout_id
        )
    }
}
