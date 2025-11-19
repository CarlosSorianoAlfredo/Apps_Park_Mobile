package com.example.apps_park

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.apps_park.databinding.ActivityAdminDashboardBinding
import com.example.apps_park.ui.dashboard.DashboardFragment
import com.example.apps_park.ui.estacionamientos.EstacionamientosFragment
import com.example.apps_park.ui.perfil.PerfilFragment // Importa el nuevo fragmento
import com.example.apps_park.ui.reservas.ReservasFragment

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            replaceFragment(DashboardFragment(), "Dashboard")
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    replaceFragment(DashboardFragment(), "Dashboard")
                    true
                }
                R.id.nav_estacionamientos -> {
                    replaceFragment(EstacionamientosFragment(), "Mis Estacionamientos")
                    true
                }
                R.id.nav_reservas -> {
                    replaceFragment(ReservasFragment(), "Gestión de Reservas")
                    true
                }
                R.id.nav_perfil -> {
                    replaceFragment(PerfilFragment(), "Perfil") // NUEVO
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
        supportActionBar?.title = title
    }
}
