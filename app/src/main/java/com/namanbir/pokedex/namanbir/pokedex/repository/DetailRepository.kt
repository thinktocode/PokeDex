

package com.namanbir.pokedex.namanbir.pokedex.repository

import androidx.annotation.WorkerThread
import com.skydoves.pokedex.model.PokemonInfo
import com.skydoves.pokedex.network.PokedexClient
import com.skydoves.pokedex.persistence.PokemonInfoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepository @Inject constructor(
  private val pokedexClient: PokedexClient,
  private val pokemonInfoDao: PokemonInfoDao
) : Repository {

  @WorkerThread
  suspend fun fetchPokemonInfo(
    name: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) = flow<PokemonInfo?> {
    val pokemonInfo = pokemonInfoDao.getPokemonInfo(name)
    if (pokemonInfo == null) {
      val response = pokedexClient.fetchPokemonInfo(name = name)
      response.suspendOnSuccess {
        data.whatIfNotNull { response ->
          pokemonInfoDao.insertPokemonInfo(response)
          emit(response)
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
      emit(pokemonInfo)
      onSuccess()
    }
  }.flowOn(Dispatchers.IO)
}
