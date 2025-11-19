package com.example.apps_park

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.apps_park.databinding.ItemReservaBinding
import com.example.apps_park.network.Reserva

class ReservasAdapter(
    private var reservas: List<Reserva>
) : RecyclerView.Adapter<ReservasAdapter.ReservaViewHolder>() {

    inner class ReservaViewHolder(val binding: ItemReservaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val binding = ItemReservaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = reservas[position]
        val context = holder.itemView.context

        with(holder.binding) {
            // Asigna datos
            nombreEstacionamientoReserva.text = reserva.nombreEstacionamiento
            precioReserva.text = "$${reserva.precioTotal}"
            statusChip.text = reserva.status

            // Lógica para colorear el chip
            val (backgroundColor, textColor) = when (reserva.status.lowercase()) {
                "confirmada" -> R.color.colorPrimary to R.color.white
                "en espera" -> R.color.status_orange to R.color.white
                "cancelada" -> R.color.status_red to R.color.white
                else -> R.color.gray to R.color.white
            }
            statusChip.setChipBackgroundColorResource(backgroundColor)
            statusChip.setTextColor(ContextCompat.getColor(context, textColor))

            // Detalles
            detalleFecha.detalleIcon.setImageResource(R.drawable.ic_calendar_today)
            detalleFecha.detalleText.text = "${reserva.fecha} • ${reserva.horario}"

            detalleDuracion.detalleIcon.setImageResource(R.drawable.ic_time)
            detalleDuracion.detalleText.text = "${reserva.duracionHoras} horas"

            detalleCodigo.detalleIcon.setImageResource(R.drawable.ic_key)
            detalleCodigo.detalleText.text = "Código: ${reserva.codigoReserva}"

            detalleUsuario.detalleIcon.setImageResource(R.drawable.ic_person)
            detalleUsuario.detalleText.text = "ID Usuario: ${reserva.usuarioId}"
        }
    }

    override fun getItemCount() = reservas.size

    fun updateData(newReservas: List<Reserva>) {
        reservas = newReservas
        notifyDataSetChanged()
    }
}