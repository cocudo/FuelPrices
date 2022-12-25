package com.iparcoc.fuelprices.model

import com.google.gson.annotations.SerializedName

data class Municipality (
    @SerializedName("IDMunicipio" ) var IDMunicipio : String,
    @SerializedName("IDProvincia" ) var IDProvincia : String,
    @SerializedName("IDCCAA"      ) var IDCCAA      : String,
    @SerializedName("Municipio"   ) var Municipio   : String,
    @SerializedName("Provincia"   ) var Provincia   : String,
    @SerializedName("CCAA"        ) var CCAA        : String
)