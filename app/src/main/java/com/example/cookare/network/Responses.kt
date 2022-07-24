package com.example.example

import com.google.gson.annotations.SerializedName


data class Responses (

  @SerializedName("localizedObjectAnnotations" ) var localizedObjectAnnotations : ArrayList<LocalizedObjectAnnotations> = arrayListOf()

)