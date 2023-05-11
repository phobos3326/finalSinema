package com.example.skillsinema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.skillsinema.databinding.ActivityMainBinding
import com.example.skillsinema.ui.main.MainFragment
import com.example.skillsinema.ui.main.ThirdFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        val navView =binding.bottomNavigationView
        val navController =findNavController(R.id.nav_host_fragment)
       val appBarConfiguration = AppBarConfiguration(
           setOf(
               R.id.home_fragment, R.id.find_fragment, R.id.person_fragment
           )
       )

        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)



    }



    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }

    }
