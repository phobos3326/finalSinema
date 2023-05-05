package com.example.skillsinema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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

        val firstFragment=MainFragment()
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
        }

    }



    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }

    }
