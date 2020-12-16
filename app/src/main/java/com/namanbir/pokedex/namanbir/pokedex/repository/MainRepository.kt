

package com.namanbir.pokedex.namanbir.pokedex.repository

import androidx.annotation.WorkerThread
import com.skydoves.pokedex.network.PokedexClient
import com.skydoves.pokedex.persistence.PokemonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
  private val pokedexClient: PokedexClient,
  private val pokemonDao: PokemonDao
) : Repository {

  @WorkerThread
  suspend fun fetchPokemonList(
    page: Int,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) = flow {
    var pokemons = pokemonDao.getPokemonList(page)
    if (pokemons.isEmpty()) {
      val response = pokedexClient.fetchPokemonList(page = page)
      response.suspendOnSuccess {
        data.whatIfNotNull { response ->
          pokemons = response.results
          pokemons.forEach { pokemon -> pokemon.page = page }
          pokemonDao.insertPokemonList(pokemons)
          emit(pokemonDao.getAllPokemonList(page))
          onSuccess()
        }
      }
        // handle the case when the API request gets an error response.
        // e.g. internal server error.
        .onError {
          onError(message())
        }
        // handle the case when the API request gets an exception response.
        // e.g. network connection error.
        .onException {
          onError(message())
        }
    } else {
      emit(pokemonDao.getAllPokemonList(page))
      onSuccess()
    }
  }.flowOn(Dispatchers.IO)
}
