package com.example.apps_park

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apps_park.databinding.ActivityEstacionamientoDuenioBinding
import com.example.apps_park.network.Estacionamiento
import com.example.apps_park.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityEstacionamientoDuenio : AppCompatActivity() {

    private lateinit var binding: ActivityEstacionamientoDuenioBinding
    private lateinit var estacionamientoAdapter: EstacionamientoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstacionamientoDuenioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        loadEstacionamientos()
    }

    private fun setupRecyclerView() {
        estacionamientoAdapter = EstacionamientoAdapter(emptyList())
        binding.recyclerViewEstacionamientos.apply {
            adapter = estacionamientoAdapter
            layoutManager = LinearLayoutManager(this@ActivityEstacionamientoDuenio)
        }
    }

    private fun loadEstacionamientos() {
        val token = getToken()
        if (token.isNullOrEmpty()) {
            startActivity(Intent(this, Activity_Login::class.java))
            finish()
            return
        }

        val authToken = "Bearer $token"
        RetrofitClient.instance.getMisEstacionamientos(authToken).enqueue(object : Callback<List<Estacionamiento>> {
            override fun onResponse(call: Call<List<Estacionamiento>>, response: Response<List<Estacionamiento>>) {
                if (response.isSuccessful) {
                    response.body()?.let { estacionamientoAdapter.updateData(it) }
                } else {
                    Toast.makeText(this@ActivityEstacionamientoDuenio, "Error al cargar estacionamientos: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Estacionamiento>>, t: Throwable) {
                Toast.makeText(this@ActivityEstacionamientoDuenio, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getToken(): String? {
        val sharedPref = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString("jwt_token", null)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
