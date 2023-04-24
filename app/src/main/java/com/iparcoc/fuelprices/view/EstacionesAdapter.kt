package com.iparcoc.fuelprices.view

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.iparcoc.fuelprices.R
import com.iparcoc.fuelprices.model.EESSTerrestres


class EstacionesAdapter(private val estaciones: ArrayList<EESSTerrestres>, private val estacionesClickListener: RecyclerViewInterface) : RecyclerView.Adapter<EstacionViewHolder>(){

    var estacionesListFiltered : ArrayList<EESSTerrestres> = ArrayList()
    //var estacionesList : ArrayList<EESSTerrestres> = ArrayList()

    //constructor?? bloque de iniciacion de variables
    init {
        //estacionesList = estaciones
        estacionesListFiltered = estaciones
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstacionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EstacionViewHolder(layoutInflater.inflate(R.layout.item_eess, parent, false))
    }

    override fun onBindViewHolder(holder: EstacionViewHolder, position: Int) {
        val item = estacionesListFiltered[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            estacionesClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return estacionesListFiltered.size
    }

    fun updateData(estaciones: ArrayList<EESSTerrestres>) {
        estacionesListFiltered.clear()
        estacionesListFiltered.addAll(estaciones)
        notifyDataSetChanged()
    }

}