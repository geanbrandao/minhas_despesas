package com.geanbrandao.minhasdespesas.ui.home

import android.os.Bundle
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.ui.base.BaseActivity
import com.geanbrandao.minhasdespesas.ui.navigation.home.HomeFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    private var selectMenu: Int = R.id.home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        createFragment(intent.getIntExtra("screen", R.id.home))
        setButtonNavigation(intent.getIntExtra("screen", R.id.home))
        createListeners()
    }

    private fun createListeners() {
        bottom_nav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    if (selectMenu != R.id.home) {
                        selectMenu = R.id.home
                        nextFragment(R.id.home)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
//                R.id.search -> {
//                    if (selectMenu != R.id.search) {
//                        selectMenu = R.id.search
//                        nextFragment(R.id.search)
//                    }
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.ticket -> {
//                    if (selectMenu != R.id.ticket) {
//                        selectMenu = R.id.ticket
//                        nextFragment(R.id.ticket)
//                    }
//                    return@setOnNavigationItemSelectedListener true
//                }
                R.id.settings -> {
                    if (selectMenu != R.id.settings) {
                        selectMenu = R.id.settings
                        nextFragment(R.id.settings)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }

        }
    }

    private fun setButtonNavigation(i: Int) {
        when (i) {
            1 -> {
                bottom_nav.selectedItemId = R.id.home
                selectMenu = R.id.home
            }
//            2 -> {
//                bottom_nav.selectedItemId = R.id.search
//                selectMenu = R.id.search
//            }
//            3 -> {
//                bottom_nav.selectedItemId = R.id.ticket
//                selectMenu = R.id.ticket
//            }
            4 -> {
                bottom_nav.selectedItemId = R.id.settings
                selectMenu = R.id.settings
            }
        }
    }

    private fun nextFragment(id: Int) {
        when (id) {
            R.id.home -> {
                val homeFragment = HomeFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, homeFragment, "home")
                    .addToBackStack(null)
                    .commit()
            }
//            R.id.search -> {
//                val homeFragment = HomeFragment.newInstance()
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment, homeFragment, "HomeFragment")
//                    .addToBackStack(null)
//                    .commit()
//            }
//            R.id.ticket -> {
//                val homeFragment = HomeFragment.newInstance()
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment, homeFragment, "HomeFragment")
//                    .addToBackStack(null)
//                    .commit()
//            }
            R.id.settings -> {
                val profileFragment = HomeFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, profileFragment, "profile")
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun createFragment(id: Int) {
        when (id) {
            R.id.home -> {
                val fragment11 = HomeFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, fragment11, "home")
                    .addToBackStack(null)
                    .commit()
            }
//            R.id.search -> {
//                val fragment11 = HomeFragment.newInstance()
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment, fragment11, "Fragment1")
//                    .addToBackStack(null)
//                    .commit()
//            }
//            R.id.ticket -> {
//                val fragment11 = HomeFragment.newInstance()
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment, fragment11, "Fragment1")
//                    .addToBackStack(null)
//                    .commit()
//            }
            R.id.settings -> {
                val fragment11 = HomeFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, fragment11, "profile")
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onBackPressed() {

    }
}