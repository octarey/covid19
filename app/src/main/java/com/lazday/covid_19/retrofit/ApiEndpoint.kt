package com.lazday.covid_19.retrofit

import com.lazday.covid_19.GlobalResponse
import com.lazday.covid_19.MainModel
import com.lazday.covid_19.ProvinceModel
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @GET("indonesia")
    fun getData(): Call<List<MainModel>>

    @GET("indonesia/provinsi")
    fun getProvince(): Call<List<ProvinceModel>>

    @GET("positif")
    fun getGlobalPositif(): Call<GlobalResponse>

    @GET("sembuh")
    fun getGlobalSembuh(): Call<GlobalResponse>

    @GET("meninggal")
    fun getGlobalMeninggal(): Call<GlobalResponse>

}