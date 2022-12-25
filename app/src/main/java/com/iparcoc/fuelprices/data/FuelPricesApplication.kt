package com.iparcoc.fuelprices.data

import android.app.Application

class FuelPricesApplication: Application() {

    companion object{
        lateinit var preferences: Preferences
    }

    override fun onCreate() {
        super.onCreate()
        preferences = Preferences(applicationContext)
    }
}