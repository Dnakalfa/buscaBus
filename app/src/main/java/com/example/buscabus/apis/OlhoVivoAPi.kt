package com.example.buscabus.apis

import com.example.buscabus.models.Corredor
import com.example.buscabus.models.Posicao
import com.example.buscabus.models.Linha
import com.example.buscabus.models.Parada
import com.example.buscabus.models.Previsao
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OlhoVivoAPI {

    @POST("/Login/Autenticar${RetrofitHelper.TOKEN}")
    suspend fun autenticar() : Boolean

    @GET("/Posicao${RetrofitHelper.TOKEN}")
    suspend fun posicaoVeiculos() : Response<Posicao>

    @GET("/Parada/Buscar${RetrofitHelper.TOKEN}")
    suspend fun buscarParadas(@Query("termosBusca") nome: String) : Response<List<Parada>>

    @GET("/Linha/Buscar${RetrofitHelper.TOKEN}")
    suspend fun buscarLinhas(@Query("termosBusca") texto: String) : Response<List<Linha>>

    @GET("/Previsao/Parada${RetrofitHelper.TOKEN}")
    suspend fun buscarPrevisaoParadas(@Query("codigoParada") codigo: Int) : Response<Previsao>

    @GET("/Corredor${RetrofitHelper.TOKEN}")
    suspend fun buscarCorredores() : Response<List<Corredor>>

}