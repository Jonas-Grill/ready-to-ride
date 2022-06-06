package com.readytoride

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.readytoride.databinding.ActivityMainBinding
import com.readytoride.ui.horse.HorseViewModel
import com.readytoride.ui.login.FragmentNavigation
import com.readytoride.ui.login.LoginFragment

class MainActivity : AppCompatActivity(), FragmentNavigation {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_horses,
                R.id.nav_trainer,
                R.id.nav_stable,
                R.id.nav_lessons,
                R.id.nav_profile,
                R.id.nav_contact
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Alex: Einbindung Loginscreen
        supportFragmentManager.beginTransaction()
            .add(R.id.drawer_layout,LoginFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //Alex: Logik für Registrierung
    override fun navigateFrag(fragment: Fragment, addtoStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.drawer_layout,fragment)

        if (addtoStack){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}