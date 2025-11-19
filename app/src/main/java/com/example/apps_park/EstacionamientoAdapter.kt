package com.example.apps_park

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apps_park.databinding.ItemEstacionamientoBinding
import com.example.apps_park.network.Estacionamiento

class EstacionamientoAdapter(
    private var estacionamientos: List<Estacionamiento>
) : RecyclerView.Adapter<EstacionamientoAdapter.EstacionamientoViewHolder>() {

    // ViewHolder que contiene las vistas de una tarjeta de estacionamiento.
    inner class EstacionamientoViewHolder(val binding: ItemEstacionamientoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstacionamientoViewHolder {
        val binding = ItemEstacionamientoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EstacionamientoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EstacionamientoViewHolder, position: Int) {
        val estacionamiento = estacionamientos[position]
        val context = holder.itemView.context

        with(holder.binding) {
            // Asigna los datos principales
            nombreEstacionamiento.text = estacionamiento.nombre
            direccionEstacionamiento.text = estacionamiento.direccion
            precioEstacionamiento.text = context.getString(R.string.precio_por_hora, estacionamiento.precio)

            // Configura los contadores
            counterTotal.counterValue.text = estacionamiento.espaciosTotal.toString()
            counterTotal.counterLabel.text = "Total"

            counterDisponibles.counterValue.text = estacionamiento.espaciosDisponibles.toString()
            counterDisponibles.counterLabel.text = "Disponibles"

            counterOcupados.counterValue.text = estacionamiento.ocupados.toString()
            counterOcupados.counterLabel.text = "Ocupados"

            counterReservados.counterValue.text = estacionamiento.reservados.toString()
            counterReservados.counterLabel.text = "Reservados"
        }
    }

    override fun getItemCount() = estacionamientos.size

    // Función para actualizar la lista de datos del adaptador.
    fun updateData(newEstacionamientos: List<Estacionamiento>) {
        estacionamientos = newEstacionamientos
        notifyDataSetChanged()
    }
}
