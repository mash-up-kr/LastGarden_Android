package com.mashup.lastgarden.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonFactory {
    val gson: Gson by lazy { GsonBuilder().create() }
}