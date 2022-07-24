package com.example.example

import com.google.gson.annotations.SerializedName


data class LocalizedObjectAnnotations (

  @SerializedName("mid"          ) var mid          : String?       = null,
  @SerializedName("name"         ) var name         : String?       = null,
  @SerializedName("score"        ) var score        : Double?       = null,
  @SerializedName("boundingPoly" ) var boundingPoly : BoundingPoly? = BoundingPoly()

)