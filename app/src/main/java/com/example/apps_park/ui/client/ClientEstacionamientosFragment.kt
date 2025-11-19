package com.example.apps_park.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apps_park.ClientEstacionamientoAdapter
import com.example.apps_park.databinding.FragmentClientEstacionamientosBinding
import com.example.apps_park.network.Estacionamiento
import com.example.apps_park.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientEstacionamientosFragment : Fragment() {

    private var _binding: FragmentClientEstacionamientosBinding? = null
    private val binding get() = _binding!!

    private lateinit var estacionamientoAdapter: ClientEstacionamientoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientEstacionamientosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadEstacionamientos()
    }

    private fun setupRecyclerView() {
        estacionamientoAdapter = ClientEstacionamientoAdapter(emptyList())
        binding.recyclerViewClientEstacionamientos.apply {
            adapter = estacionamientoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadEstacionamientos() {
        // CORREGIDO: Llamar a la función pública sin token
        RetrofitClient.instance.getEstacionamientosPublicos().enqueue(object : Callback<List<Estacionamiento>> {
            override fun onResponse(call: Call<List<Estacionamiento>>, response: Response<List<Estacionamiento>>) {
                if (!isAdded) return
                if (response.isSuccessful) {
                    response.body()?.let { estacionamientoAdapter.updateData(it) }
                } else {
                    Toast.makeText(requireContext(), "Error al cargar estacionamientos: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Estacionamiento>>, t: Throwable) {
                if (!isAdded) return
                Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
