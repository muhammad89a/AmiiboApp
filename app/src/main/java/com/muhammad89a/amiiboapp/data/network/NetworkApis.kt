package com.muhammad89a.amiiboapp.data.network

import com.muhammad89a.amiiboapp.data.model.AmiiboContainerDTO
import com.muhammad89a.amiiboapp.data.model.AmiiboDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApis {

    @GET("amiibo/")
    suspend fun getAllAmiibos(): AmiiboContainerDTO

//    @GET("/")
//    suspend fun getAmiiboByID(@Query("id") name: String): AmiiboDTO?

}