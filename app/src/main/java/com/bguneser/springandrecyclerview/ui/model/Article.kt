package com.bguneser.springandrecyclerview.ui.model

import com.google.gson.annotations.SerializedName

data class Article (@SerializedName("id") val id : Long, @SerializedName("title") val title:String, @SerializedName("content") val content : String)