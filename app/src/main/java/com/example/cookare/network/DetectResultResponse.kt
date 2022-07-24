package com.example.example

import com.google.gson.annotations.SerializedName


data class DetectResultResponse (

  @SerializedName("responses" ) var responses : ArrayList<Responses> = arrayListOf()

)