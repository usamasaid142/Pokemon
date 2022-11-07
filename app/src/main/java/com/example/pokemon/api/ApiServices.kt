package com.example.pokemon.api

import com.example.pokemon.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("pokemon")
    suspend fun getPokemon():Response<PokemonResponse>
}