package com.example.apps_park.ui.client

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.apps_park.Activity_Login
import com.example.apps_park.databinding.FragmentClientPerfilBinding
import com.example.apps_park.network.PerfilResponse
import com.example.apps_park.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientPerfilFragment : Fragment() {

    private var _binding: FragmentClientPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPerfilData()

        binding.clientLogoutButton.setOnClickListener {
            logout()
        }
    }

    private fun loadPerfilData() {
        val token = getToken()
        if (token.isNullOrEmpty()) {
            logout()
            return
        }

        val authToken = "Bearer $token"
        RetrofitClient.instance.getPerfil(authToken).enqueue(object : Callback<PerfilResponse> {
            override fun onResponse(call: Call<PerfilResponse>, response: Response<PerfilResponse>) {
                if (!isAdded) return
                if (response.isSuccessful) {
                    response.body()?.let { binding.clientProfileName.text = it.nombre }
                } else {
                    Toast.makeText(requireContext(), "Error al cargar el perfil: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PerfilResponse>, t: Throwable) {
                if (!isAdded) return
                Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun logout() {
        val sharedPref = activity?.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        sharedPref?.edit()?.remove("jwt_token")?.apply()

        val intent = Intent(requireContext(), Activity_Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun getToken(): String? {
        return activity?.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)?.getString("jwt_token", null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
