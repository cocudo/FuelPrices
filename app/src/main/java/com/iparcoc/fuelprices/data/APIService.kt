package com.iparcoc.fuelprices.data

import com.iparcoc.fuelprices.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getInfoEESS(@Url url:String) : Call<ListaEESSTerrestresResponse>

    @GET
    fun getCCAA(@Url url:String) : Call<ArrayList<CCAA>>

    @GET
    fun getProvincesFromCCAA(@Url url:String) : Call<ArrayList<Province>>

    @GET
    fun getMunicipiesFromProvince(@Url url:String) : Call<ArrayList<Municipality>>

    @GET
    fun getProducts(@Url url:String) : Call<ArrayList<Product>>

}