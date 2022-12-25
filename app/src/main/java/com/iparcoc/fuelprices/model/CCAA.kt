package com.iparcoc.fuelprices.model

import com.google.gson.annotations.SerializedName

data class CCAA (
    @SerializedName("IDCCAA" ) var IDCCAA : String,
    @SerializedName("CCAA"   ) var CCAA   : String
)