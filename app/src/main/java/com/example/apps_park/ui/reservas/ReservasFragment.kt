package com.example.apps_park.ui.reservas

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
import com.example.apps_park.ReservasAdapter
import com.example.apps_park.databinding.FragmentReservasBinding
import com.example.apps_park.network.Reserva
import com.example.apps_park.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservasFragment : Fragment() {

    private var _binding: FragmentReservasBinding? = null
    private val binding get() = _binding!!

    private lateinit var reservasAdapter: ReservasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadReservas()
    }

    private fun setupRecyclerView() {
        reservasAdapter = ReservasAdapter(emptyList())
        binding.recyclerViewReservas.apply {
            adapter = reservasAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadReservas() {
        val token = getToken()
        if (token.isNullOrEmpty()) {
            startActivity(Intent(requireContext(), Activity_Login::class.java))
            activity?.finish()
            return
        }

        val authToken = "Bearer $token"
        RetrofitClient.instance.getReservas(authToken).enqueue(object : Callback<List<Reserva>> {
            override fun onResponse(call: Call<List<Reserva>>, response: Response<List<Reserva>>) {
                if (!isAdded) return
                if (response.isSuccessful) {
                    response.body()?.let { reservasAdapter.updateData(it) }
                } else {
                    Toast.makeText(requireContext(), "Error al cargar reservas: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Reserva>>, t: Throwable) {
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
