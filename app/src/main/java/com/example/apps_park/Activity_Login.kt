package com.example.apps_park

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apps_park.databinding.Activity1Binding
import com.example.apps_park.network.LoginRequest
import com.example.apps_park.network.LoginResponse
import com.example.apps_park.network.RetrofitClient
import com.example.apps_park.network.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_Login : AppCompatActivity() {

    private lateinit var binding: Activity1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            handleLogin()
        }

        binding.signupText.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleLogin() {
        val email = binding.username.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        val request = LoginRequest(email = email, password = password)

        RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        saveAuthData(loginResponse.token, loginResponse.usuario.rol)
                        
                        Toast.makeText(this@Activity_Login, "Login exitoso", Toast.LENGTH_LONG).show()
                        
                        val intent = if (loginResponse.usuario.rol.equals("duenio", ignoreCase = true)) {
                            Intent(this@Activity_Login, AdminDashboardActivity::class.java)
                        } else {
                            Intent(this@Activity_Login, ClientDashboardActivity::class.java)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@Activity_Login, "Respuesta inesperada del servidor", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@Activity_Login, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@Activity_Login, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveAuthData(token: String, rol: String) {
        val sharedPref = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("jwt_token", token)
            putString("user_rol", rol)
            apply()
        }
    }
}
