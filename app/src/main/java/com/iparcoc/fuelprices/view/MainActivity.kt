package com.iparcoc.fuelprices.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.iparcoc.fuelprices.databinding.ActivityMainBinding
import com.iparcoc.fuelprices.model.EESSTerrestres
import com.iparcoc.fuelprices.model.ListaEESSTerrestresResponse
import com.iparcoc.fuelprices.data.APIService
import com.iparcoc.fuelprices.data.FuelPricesApplication.Companion.preferences
import retrofit2.Retrofit
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener,
    RecyclerViewInterface{

    private lateinit var binding: ActivityMainBinding
    var estacionesList : ArrayList<EESSTerrestres> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //check the shared preferences
        checkUserValues()
        setContentView(binding.root)
        searchByTown("2196")

        binding.searchEESS.setOnQueryTextListener(this)
    }


    private fun checkUserValues(){
        if(preferences.getUserName().isEmpty()){
            goToUserPreferences()
        }
    }

    private fun goToUserPreferences(){
        startActivity(Intent(this, PreferencesActivity::class.java))
    }

    private fun initRecyclerView(eessTerrestres: ArrayList<EESSTerrestres>){
        binding.rvEstacionesList.layoutManager = LinearLayoutManager(this)
        binding.rvEstacionesList.adapter = EstacionesAdapter(eessTerrestres, this)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroMunicipio/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByTown(query: String){
        doAsync {
            val call = getRetrofit().create(APIService::class.java).getInfoEESS("$query").execute()
            val eess = call.body() as ListaEESSTerrestresResponse
            uiThread {
                estacionesList.addAll(eess.ListaEESSPrecio)
                initRecyclerView(eess.ListaEESSPrecio)

            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //searchByName(query.toString().toLowerCase())
        //var listaEESSFiltrada = (binding.rvEstacionesList.adapter as EstacionesAdapter).estacionesList
        var listaEESSFiltrada = estacionesList
        listaEESSFiltrada = listaEESSFiltrada.filter { x -> x.direccion.lowercase().contains(query.toString().lowercase()) } as ArrayList<EESSTerrestres>
        if(listaEESSFiltrada.size > 0){
            (binding.rvEstacionesList.adapter as EstacionesAdapter).updateData(listaEESSFiltrada)
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText.toString().isEmpty() || newText.toString().isBlank()){
            var listaEESS = estacionesList
            if(listaEESS.size > 0){
                (binding.rvEstacionesList.adapter as EstacionesAdapter).updateData(listaEESS)
                return true
            }
        }
        return false
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this,"Clicked in " + position + " item", Toast.LENGTH_SHORT).show()
    }


}