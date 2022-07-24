package com.example.example

import com.google.gson.annotations.SerializedName


data class NormalizedVertices (

  @SerializedName("x" ) var x : Double? = null,
  @SerializedName("y" ) var y : Double? = null

)