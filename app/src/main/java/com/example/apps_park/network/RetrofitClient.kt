package com.example.apps_park.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // La URL que te dio Ngrok
    private const val BASE_URL = "https://semiphilosophical-macie-glykopectic.ngrok-free.dev/"

    // Interceptor para poder ver el cuerpo de las peticiones y respuestas en el Logcat.
    // Es muy útil para depurar.
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Cliente HTTP que incluye el interceptor de logging.
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Instancia única de Retrofit para toda la app.
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient) // Asigna el cliente con el logger.
            .build()
        
        // Crea una implementación de nuestra interfaz ApiService.
        retrofit.create(ApiService::class.java)
    }
}
