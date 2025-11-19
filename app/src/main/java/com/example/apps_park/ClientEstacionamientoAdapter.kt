package com.example.apps_park

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apps_park.databinding.ItemClientEstacionamientoBinding
import com.example.apps_park.network.Estacionamiento

class ClientEstacionamientoAdapter(
    private var estacionamientos: List<Estacionamiento>
) : RecyclerView.Adapter<ClientEstacionamientoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemClientEstacionamientoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClientEstacionamientoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estacionamiento = estacionamientos[position]
        val context = holder.itemView.context

        with(holder.binding) {
            clientNombreEstacionamiento.text = estacionamiento.nombre
            clientDireccionEstacionamiento.text = estacionamiento.direccion
            clientPrecioEstacionamiento.text = context.getString(R.string.precio_por_hora, estacionamiento.precio)
            clientDisponibles.text = "${estacionamiento.espaciosDisponibles} Disponibles"
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetalleEstacionamientoActivity::class.java).apply {
                putExtra("ESTACIONAMIENTO_ID", estacionamiento.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = estacionamientos.size

    fun updateData(newEstacionamientos: List<Estacionamiento>) {
        estacionamientos = newEstacionamientos
        notifyDataSetChanged()
    }
}
