package com.example.skillsinema

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
//import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.skillsinema.databinding.ActivityMainBinding
import com.example.skillsinema.ui.main.menu.CollectionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //val actionBar = supportActionBar


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val actionBar= supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
       // actionBar?.setBackgroundDrawable(ColorDrawable(Color.alpha(90)))
        actionBar?.let {
            it.setTitle("1111")

        }


       // CollectionDialog().show(supportFragmentManager, "bottomSheetDialogFragment.tag")

       // actionBar?.setTitle("5454544")

        /*    val firstFragment=MainFragment()
            val secondFragment=SecondFragment()
            val thirdFragment= ThirdFragment()

            setCurrentFragment(firstFragment)

           binding.bottomNavigationView.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.home_fragment->setCurrentFragment(firstFragment)
                    R.id.find_fragment->setCurrentFragment(secondFragment)
                    R.id.person_fragment->setCurrentFragment(thirdFragment)



                }
                true
            }*/


        val navView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_fragment, R.id.find_fragment, R.id.person_fragment
            )


        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle home button press (navigate back or perform another action)
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun obtainNavHostFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        navGraphId: Int,
        containerId: Int
    ): NavHostFragment {
        // If the Nav Host fragment exists, return it
        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
        existingFragment?.let { return it }

        // Otherwise, create it and return it.
        val navHostFragment = NavHostFragment.create(navGraphId)
        fragmentManager.beginTransaction()
            .add(containerId, navHostFragment, fragmentTag)
            .commitNow()
        return navHostFragment
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            commit()
        }

}
