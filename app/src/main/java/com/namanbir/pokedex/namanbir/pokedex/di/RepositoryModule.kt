

package com.namanbir.pokedex.namanbir.pokedex.di

import com.skydoves.pokedex.network.PokedexClient
import com.skydoves.pokedex.persistence.PokemonDao
import com.skydoves.pokedex.persistence.PokemonInfoDao
import com.skydoves.pokedex.repository.DetailRepository
import com.skydoves.pokedex.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

  @Provides
  @ActivityRetainedScoped
  fun provideMainRepository(
    pokedexClient: PokedexClient,
    pokemonDao: PokemonDao
  ): MainRepository {
    return MainRepository(pokedexClient, pokemonDao)
  }

  @Provides
  @ActivityRetainedScoped
  fun provideDetailRepository(
    pokedexClient: PokedexClient,
    pokemonInfoDao: PokemonInfoDao
  ): DetailRepository {
    return DetailRepository(pokedexClient, pokemonInfoDao)
  }
}
