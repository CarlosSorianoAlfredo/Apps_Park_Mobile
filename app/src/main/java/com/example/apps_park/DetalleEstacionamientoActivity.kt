package com.example.apps_park

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apps_park.databinding.ActivityDetalleEstacionamientoBinding
import com.example.apps_park.network.Estacionamiento
import com.example.apps_park.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleEstacionamientoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleEstacionamientoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleEstacionamientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val estacionamientoId = intent.getIntExtra("ESTACIONAMIENTO_ID", -1)
        if (estacionamientoId != -1) {
            loadEstacionamientoDetalle(estacionamientoId)
        } else {
            Toast.makeText(this, "Error: ID de estacionamiento no válido", Toast.LENGTH_LONG).show()
            finish()
        }

        binding.fabReservar.setOnClickListener {
            Toast.makeText(this, "Función de reserva (en construcción)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadEstacionamientoDetalle(id: Int) {
        RetrofitClient.instance.getEstacionamientoById(id).enqueue(object : Callback<Estacionamiento> {
            override fun onResponse(call: Call<Estacionamiento>, response: Response<Estacionamiento>) {
                if (response.isSuccessful) {
                    response.body()?.let { updateUI(it) }
                } else {
                    Toast.makeText(this@DetalleEstacionamientoActivity, "Error al cargar detalles: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Estacionamiento>, t: Throwable) {
                Toast.makeText(this@DetalleEstacionamientoActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateUI(estacionamiento: Estacionamiento) {
        binding.collapsingToolbar.title = estacionamiento.nombre
        // Aquí puedes añadir la lógica para poblar los demás campos de la UI
        // Por ejemplo: binding.detalleDireccion.text = estacionamiento.direccion
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
