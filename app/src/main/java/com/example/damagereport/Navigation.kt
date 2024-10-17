package com.example.damagereport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Navigation : AppCompatActivity() {

    private lateinit var bnv: BottomNavigationView
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        username = intent.getStringExtra("USERNAME") ?: ""

        bnv = findViewById(R.id.bottomNavigationView)

        bnv.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.profile -> {
                    val profileFragment = ProfileFragment().apply {
                        arguments = Bundle().apply {
                            putString("USERNAME", username)
                        }
                    }
                    loadFragment(profileFragment)
                    true
                }
                else -> false
            }
        }

        loadFragment(HomeFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}