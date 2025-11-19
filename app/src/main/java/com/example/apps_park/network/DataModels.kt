package com.example.apps_park.network

import com.google.gson.annotations.SerializedName

// --- Modelos para el Login ---
data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class Usuario(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("rol") val rol: String
)

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("usuario") val usuario: Usuario
)

// --- Modelos para el Registro ---
data class RegisterRequest(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("rol") val rol: String
)

data class RegisterResponse(
    @SerializedName("message") val message: String
)

// --- Modelos para el Dashboard ---
data class DashboardResponse(
    @SerializedName("nombre_usuario") val nombreUsuario: String,
    @SerializedName("ingresos_hoy") val ingresosHoy: Double,
    @SerializedName("ingresos_mes") val ingresosMes: Double,
    @SerializedName("total_estacionamientos") val totalEstacionamientos: Int,
    @SerializedName("total_cajones") val totalCajones: Int,
    @SerializedName("ocupados") val ocupados: Int,
    @SerializedName("reservados") val reservados: Int,
    @SerializedName("disponibles") val disponibles: Int,
    @SerializedName("actividad_reciente") val actividadReciente: List<ActividadRecienteItem>
)

data class ActividadRecienteItem(
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("tiempo") val tiempo: String
)

// --- Modelos para Estacionamientos ---
data class Estacionamiento(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("direccion") val direccion: String,
    @SerializedName("precio") val precio: Double,
    @SerializedName("espacios_total") val espaciosTotal: Int,
    @SerializedName("espacios_disponibles") val espaciosDisponibles: Int,
    @SerializedName("ocupados") val ocupados: Int,
    @SerializedName("reservados") val reservados: Int
)

// --- Modelos para Reservas ---
data class Reserva(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre_estacionamiento") val nombreEstacionamiento: String,
    @SerializedName("precio_total") val precioTotal: Double,
    @SerializedName("status") val status: String,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("horario") val horario: String,
    @SerializedName("duracion_horas") val duracionHoras: Int,
    @SerializedName("codigo_reserva") val codigoReserva: String,
    @SerializedName("usuario_id") val usuarioId: String
)

// --- Modelos para Perfil ---
data class PerfilResponse(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("rol") val rol: String,
    @SerializedName("estacionamientos_count") val estacionamientosCount: Int,
    @SerializedName("clientes_atendidos") val clientesAtendidos: Int,
    @SerializedName("calificacion") val calificacion: Double
)
