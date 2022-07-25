package com.example.example

import com.google.gson.annotations.SerializedName


data class BoundingPoly (

  @SerializedName("normalizedVertices" ) var normalizedVertices : ArrayList<NormalizedVertices> = arrayListOf()

)