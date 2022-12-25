package com.iparcoc.fuelprices.data

import android.content.Context

class Preferences(val context:Context) {

    // Constants
    val SHARED_PREFS_NAME = "UserPreferences"
    val SHARED_USER_NAME = "username"
    val SHARED_CCAA = "ccaa"
    val SHARED_MUNICIPALITY = "minicipality"
    val SHARED_PROVINCE = "province"
    val SHARED_PRODUCT = "product"
    val SHARED_PRODUCT2 = "product2"
    val SHARED_FAVORITE_EETT = "eettfavorite"

    // Variables
    val storage = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserName(name:String){
        storage.edit().putString(SHARED_USER_NAME, name).apply()
    }

    fun saveCCAA(ccaa:String){
        storage.edit().putString(SHARED_CCAA, ccaa).apply()
    }

    fun saveMunicipality(municipality:String){
        storage.edit().putString(SHARED_MUNICIPALITY, municipality).apply()
    }

    fun saveProvince(province:String){
        storage.edit().putString(SHARED_PROVINCE, province).apply()
    }

    fun saveProduct(product:String){
        storage.edit().putString(SHARED_PRODUCT, product).apply()
    }

    fun saveProduct2(product2:String){
        storage.edit().putString(SHARED_PRODUCT2, product2).apply()
    }

    fun saveFavoriteEETT(favoriteEETT:String){
        storage.edit().putString(SHARED_FAVORITE_EETT, favoriteEETT).apply()
    }

    fun getUserName():String{
        return storage.getString(SHARED_USER_NAME, "")!!
    }

    fun getCCAA():String{
        return storage.getString(SHARED_CCAA, "")!!
    }

    fun getMunicipality():String{
        return storage.getString(SHARED_MUNICIPALITY, "")!!
    }

    fun getProvince():String{
        return storage.getString(SHARED_PROVINCE, "")!!
    }

    fun getProduct():String{
        return storage.getString(SHARED_PRODUCT, "")!!
    }

    fun getProduct2():String{
        return storage.getString(SHARED_PRODUCT2, "")!!
    }

    fun getFavoriteEETT():String{
        return storage.getString(SHARED_FAVORITE_EETT, "")!!
    }

}