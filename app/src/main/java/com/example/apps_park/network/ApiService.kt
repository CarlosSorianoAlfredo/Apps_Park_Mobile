package com.example.apps_park.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/usuarios")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("dashboard")
    fun getDashboardData(@Header("Authorization") token: String): Call<DashboardResponse>

    @GET("estacionamientos")
    fun getMisEstacionamientos(@Header("Authorization") token: String): Call<List<Estacionamiento>>

    @GET("estacionamientos") 
    fun getEstacionamientosPublicos(): Call<List<Estacionamiento>>

    @GET("reservas")
    fun getReservas(@Header("Authorization") token: String): Call<List<Reserva>>

    @GET("user") 
    fun getPerfil(@Header("Authorization") token: String): Call<PerfilResponse>

    // Endpoint para obtener un estacionamiento por su ID (RESTAURADO)
    @GET("estacionamientos/{id}")
    fun getEstacionamientoById(@Path("id") id: Int): Call<Estacionamiento>
}
