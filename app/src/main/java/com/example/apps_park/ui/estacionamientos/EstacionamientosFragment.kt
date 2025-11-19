package com.example.apps_park.ui.estacionamientos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apps_park.Activity_Login
import com.example.apps_park.EstacionamientoAdapter
import com.example.apps_park.databinding.FragmentEstacionamientosBinding
import com.example.apps_park.network.Estacionamiento
import com.example.apps_park.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EstacionamientosFragment : Fragment() {

    private var _binding: FragmentEstacionamientosBinding? = null
    private val binding get() = _binding!!

    private lateinit var estacionamientoAdapter: EstacionamientoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEstacionamientosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadEstacionamientos()
    }

    private fun setupRecyclerView() {
        estacionamientoAdapter = EstacionamientoAdapter(emptyList())
        binding.recyclerViewEstacionamientos.apply {
            adapter = estacionamientoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadEstacionamientos() {
        val token = getToken()
        if (token.isNullOrEmpty()) {
            startActivity(Intent(requireContext(), Activity_Login::class.java))
            activity?.finish()
            return
        }

        val authToken = "Bearer $token"
        // CORREGIDO: Llamar a la función correcta para dueños
        RetrofitClient.instance.getMisEstacionamientos(authToken).enqueue(object : Callback<List<Estacionamiento>> {
            override fun onResponse(call: Call<List<Estacionamiento>>, response: Response<List<Estacionamiento>>) {
                if (!isAdded) return
                if (response.isSuccessful) {
                    response.body()?.let { estacionamientoAdapter.updateData(it) }
                } else {
                    Toast.makeText(requireContext(), "Error al cargar mis estacionamientos: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Estacionamiento>>, t: Throwable) {
                if (!isAdded) return
                Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getToken(): String? {
        return activity?.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)?.getString("jwt_token", null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
