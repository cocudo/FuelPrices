package com.iparcoc.fuelprices.view

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.iparcoc.fuelprices.data.APIService
import com.iparcoc.fuelprices.data.FuelPricesApplication
import com.iparcoc.fuelprices.databinding.ActivityPreferencesBinding
import com.iparcoc.fuelprices.model.CCAA
import com.iparcoc.fuelprices.model.Municipality
import com.iparcoc.fuelprices.model.Product
import com.iparcoc.fuelprices.model.Province
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreferencesBinding
    var ccaaList = ArrayList<CCAA>()
    var provincesList = ArrayList<Province>()
    var municipalitiesList = ArrayList<Municipality>()
    var productsList = ArrayList<Product>()
    var ccaaSelect = ""
    var provinceSelect = ""
    var municipalitySelect = ""
    var productSelect = ""
    val URL_BASE = "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/Listados"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllCCAA()
        getAllProducts()
        val btn: Button = binding.btnSave
        btn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                FuelPricesApplication.preferences.saveCCAA(ccaaSelect)
                FuelPricesApplication.preferences.saveProvince(provinceSelect)
                FuelPricesApplication.preferences.saveMunicipality(municipalitySelect)
                FuelPricesApplication.preferences.saveProduct(productSelect)
                FuelPricesApplication.preferences.saveUserName(binding.etUserName.text.toString())

                Toast.makeText(applicationContext, "Se han guardado las preferencias", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun getRetrofit(method: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(geturlBase(method))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun geturlBase(method:String): String{
        return URL_BASE + method
    }

    private fun initSpinner(){
        val spinner: Spinner = binding.ccaaSpinner
        val ccaaMap = ccaaList.map { it.CCAA }

        val aaCCAA = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, ccaaMap.toTypedArray())
        spinner.adapter = aaCCAA

        spinner.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                ccaaSelect = ccaaList[p2].IDCCAA
                getProvincesForCommunity(ccaaList[p2].IDCCAA)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun initSpinnerProducts(){
        val spinner: Spinner = binding.productSpinner
        val productMap = productsList.map { it.NombreProducto }

        val aaProduct = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, productMap.toTypedArray())
        spinner.adapter = aaProduct

        spinner.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                productSelect = productsList[p2].IDProducto
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun initSpinnerProvince(){
        val spinner: Spinner = binding.provincesSpinner
        val provincesMap = provincesList.map { it.Provincia }

        val aaProvinces = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, provincesMap.toTypedArray())
        spinner.adapter = aaProvinces

        spinner.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                provinceSelect = provincesList[p2].IDPovincia
                getMunicipalitiesForProvince(provincesList[p2].IDPovincia)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun initSpinnerMunicipalities(){
        val spinner: Spinner = binding.municipalitiesSpinner
        val municipalitiesMap = municipalitiesList.map { it.Municipio }

        val aaMunicipalities = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, municipalitiesMap.toTypedArray())
        spinner.adapter = aaMunicipalities

        spinner.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                municipalitySelect = municipalitiesList[p2].IDMunicipio
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun getAllCCAA(){
        doAsync {
            val call = getRetrofit("/ComunidadesAutonomas/").create(APIService::class.java).getCCAA("").execute()
            val ccaa = call.body() as ArrayList<CCAA>
            uiThread {
                ccaaList.addAll(ccaa)
                initSpinner()
            }
        }
    }

    private fun getProvincesForCommunity(idCCAA: String){
        doAsync {
            val call = getRetrofit("/ProvinciasPorComunidad/").create(APIService::class.java).getProvincesFromCCAA(idCCAA).execute()
            val provinces = call.body() as ArrayList<Province>
            uiThread {
                provincesList.clear()
                provincesList.addAll(provinces)
                initSpinnerProvince()
            }
        }
    }

    private fun getMunicipalitiesForProvince(idProvince: String){
        doAsync {
            val call = getRetrofit("/MunicipiosPorProvincia/").create(APIService::class.java).getMunicipiesFromProvince(idProvince).execute()
            val municipalities = call.body() as ArrayList<Municipality>
            uiThread {
                municipalitiesList.clear()
                municipalitiesList.addAll(municipalities)
                initSpinnerMunicipalities()
            }
        }
    }


    private fun getAllProducts(){
        doAsync {
            val call = getRetrofit("/ProductosPetroliferos/").create(APIService::class.java).getProducts("").execute()
            val product = call.body() as ArrayList<Product>
            uiThread {
                productsList.addAll(product)
                initSpinnerProducts()
            }
        }
    }

    private fun getAllProvinces(){
        doAsync {
            val call = getRetrofit("/MunicipiosPorProvincia/").create(APIService::class.java).getCCAA("").execute()
            val ccaa = call.body() as ArrayList<CCAA>
            uiThread {
                ccaaList.addAll(ccaa)
                initSpinner()
            }
        }
    }

    private fun getAllMunicipalities(){
        doAsync {
            val call = getRetrofit("/MunicipiosPorProvincia/").create(APIService::class.java).getCCAA("").execute()
            val ccaa = call.body() as ArrayList<CCAA>
            uiThread {
                ccaaList.addAll(ccaa)
                initSpinner()
            }
        }
    }


}