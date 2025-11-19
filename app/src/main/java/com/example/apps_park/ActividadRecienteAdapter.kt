package com.example.apps_park

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apps_park.databinding.ItemActividadRecienteBinding
import com.example.apps_park.network.ActividadRecienteItem

class ActividadRecienteAdapter(
    private var items: List<ActividadRecienteItem>
) : RecyclerView.Adapter<ActividadRecienteAdapter.ActividadViewHolder>() {

    inner class ActividadViewHolder(val binding: ItemActividadRecienteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActividadViewHolder {
        val binding = ItemActividadRecienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActividadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActividadViewHolder, position: Int) {
        val item = items[position]
        holder.binding.itemTitle.text = item.descripcion
        holder.binding.itemSubtitle.text = item.tiempo
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<ActividadRecienteItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
