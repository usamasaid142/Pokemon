package com.example.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.model.PokemonResponse
import com.example.pokemon.repo.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewmodel @Inject constructor(private val repo:PokemonRepository):ViewModel() {

    private val _response=MutableLiveData<PokemonResponse>()
    val response:LiveData<PokemonResponse> get() = _response

    init {
        getPokemon()
    }

    private fun getPokemon()=viewModelScope.launch(Dispatchers.IO) {

        repo.getPokemon().let { response ->
            if (response.isSuccessful){
                _response.postValue(response.body())
            }else{
            }
        }

    }
}