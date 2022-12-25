package com.iparcoc.fuelprices.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("IDProducto"                ) var IDProducto                : String,
    @SerializedName("NombreProducto"            ) var NombreProducto            : String,
    @SerializedName("NombreProductoAbreviatura" ) var NombreProductoAbreviatura : String
)