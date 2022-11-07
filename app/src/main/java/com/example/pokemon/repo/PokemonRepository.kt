package com.example.pokemon.repo

import com.example.pokemon.api.ApiServices
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api:ApiServices) {

    suspend fun getPokemon()=api.getPokemon()
}