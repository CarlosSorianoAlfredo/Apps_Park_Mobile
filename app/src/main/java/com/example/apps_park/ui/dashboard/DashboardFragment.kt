package com.example.apps_park.ui.dashboard

import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apps_park.Activity_Login
import com.example.apps_park.ActividadRecienteAdapter
import com.example.apps_park.databinding.FragmentDashboardBinding
import com.example.apps_park.network.DashboardResponse
import com.example.apps_park.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var actividadAdapter: ActividadRecienteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadDashboardData()
    }

    private fun setupRecyclerView() {
        actividadAdapter = ActividadRecienteAdapter(emptyList())
        binding.recyclerActividadReciente.apply {
            adapter = actividadAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = false
        }
    }

    private fun loadDashboardData() {
        val token = getToken()
        if (token.isNullOrEmpty()) {
            // No es necesario un Toast aquí, la redirección es suficiente.
            startActivity(Intent(requireContext(), Activity_Login::class.java))
            activity?.finish()
            return
        }

        val authToken = "Bearer $token"
        RetrofitClient.instance.getDashboardData(authToken).enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(call: Call<DashboardResponse>, response: Response<DashboardResponse>) {
                // CORREGIDO: Comprobar si el fragmento todavía está activo.
                if (!isAdded) return

                if (response.isSuccessful) {
                    response.body()?.let { updateUI(it) }
                } else {
                    Toast.makeText(requireContext(), "Error al cargar datos: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                // CORREGIDO: Comprobar si el fragmento todavía está activo.
                if (!isAdded) return

                Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateUI(data: DashboardResponse) {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
        binding.greetingText.text = "Hola, ${data.nombreUsuario}! 👋"
        // Aquí iría la lógica para actualizar las tarjetas, que ahora están en fragment_dashboard.xml
    }

    private fun getToken(): String? {
        return activity?.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)?.getString("jwt_token", null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Limpia la referencia al binding para evitar fugas de memoria
    }
}
