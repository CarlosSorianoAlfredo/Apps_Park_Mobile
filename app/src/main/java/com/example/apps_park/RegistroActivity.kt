package com.example.apps_park

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apps_park.network.RegisterRequest
import com.example.apps_park.network.RegisterResponse
import com.example.apps_park.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {

    private var selectedRole: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val nameEditText = findViewById<EditText>(R.id.name_edit_text)
        val emailEditText = findViewById<EditText>(R.id.email_edit_text)
        val passwordEditText = findViewById<EditText>(R.id.password_edit_text_register)
        val roleSpinner = findViewById<AutoCompleteTextView>(R.id.role_spinner)
        val registerButton = findViewById<Button>(R.id.register_button)

        // --- Configuración del Spinner ---
        val roles = resources.getStringArray(R.array.role_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        roleSpinner.setAdapter(adapter)

        roleSpinner.setOnItemClickListener { parent, _, position, _ ->
            selectedRole = parent.getItemAtPosition(position).toString()
        }

        // --- Lógica del botón de registro con llamada a la API ---
        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validaciones locales (ya implementadas)
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || selectedRole.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!name.matches(Regex("^[a-zA-Z\\s]+$"))) {
                Toast.makeText(this, "El nombre solo puede contener letras y espacios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Por favor, introduce un correo electrónico válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // CORREGIDO: Usar los nombres de parámetro correctos ('nombre' y 'rol')
            val request = RegisterRequest(nombre = name, email = email, password = password, rol = selectedRole)

            RetrofitClient.instance.register(request).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        // Éxito: El usuario se registró en la base de datos
                        Toast.makeText(this@RegistroActivity, "Registrado correctamente", Toast.LENGTH_LONG).show()
                        
                        // Redirigir a la pantalla de login
                        val intent = Intent(this@RegistroActivity, Activity_Login::class.java)
                        startActivity(intent)
                        finish() // Cierra esta actividad
                    } else {
                        // Fallo: El servidor respondió con un error (ej. email duplicado)
                        Toast.makeText(this@RegistroActivity, "Error en el registro. Es posible que el correo ya esté en uso.", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    // Error de Red: No se pudo conectar con el servidor
                    Toast.makeText(this@RegistroActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
