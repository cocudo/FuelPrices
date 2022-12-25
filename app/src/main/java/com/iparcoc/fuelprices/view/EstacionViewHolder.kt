package com.iparcoc.fuelprices.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iparcoc.fuelprices.R
import com.iparcoc.fuelprices.model.EESSTerrestres

class EstacionViewHolder(view: View): RecyclerView.ViewHolder(view){

    val localidad = view.findViewById<TextView>(R.id.tvLocalidad)
    val direccion = view.findViewById<TextView>(R.id.tvDireccion)
    val horario = view.findViewById<TextView>(R.id.tvHorario)

    fun render(estacion: EESSTerrestres){
        localidad.text = estacion.localidad
        direccion.text = estacion.direccion
        horario.text = estacion.horario
    }
}