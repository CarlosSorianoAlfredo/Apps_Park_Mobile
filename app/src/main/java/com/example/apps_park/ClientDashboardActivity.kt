package com.example.apps_park

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.apps_park.databinding.ActivityClientDashboardBinding
import com.example.apps_park.ui.client.ClientEstacionamientosFragment

class ClientDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            replaceFragment(ClientEstacionamientosFragment(), "Estacionamientos")
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationClient.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_client_estacionamientos -> {
                    replaceFragment(ClientEstacionamientosFragment(), "Estacionamientos")
                    true
                }
                R.id.nav_client_reservas -> {
                    Toast.makeText(this, "Mis Reservas (en construcción)", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_client_perfil -> {
                    Toast.makeText(this, "Perfil (en construcción)", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_client, fragment)
            .commit()
        supportActionBar?.title = title
    }
}
