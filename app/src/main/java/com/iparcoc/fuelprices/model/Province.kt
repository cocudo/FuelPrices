package com.iparcoc.fuelprices.model

import com.google.gson.annotations.SerializedName

data class Province(
    @SerializedName("IDPovincia" ) var IDPovincia : String,
    @SerializedName("IDCCAA"     ) var IDCCAA     : String,
    @SerializedName("Provincia"  ) var Provincia  : String,
    @SerializedName("CCAA"       ) var CCAA       : String
)