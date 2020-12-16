

package com.namanbir.pokedex.namanbir.pokedex.di

import com.skydoves.pokedex.network.HttpRequestInterceptor
import com.skydoves.pokedex.network.PokedexClient
import com.skydoves.pokedex.network.PokedexService
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(HttpRequestInterceptor())
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://pokeapi.co/api/v2/")
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
      .build()
  }

  @Provides
  @Singleton
  fun providePokedexService(retrofit: Retrofit): PokedexService {
    return retrofit.create(PokedexService::class.java)
  }

  @Provides
  @Singleton
  fun providePokedexClient(pokedexService: PokedexService): PokedexClient {
    return PokedexClient(pokedexService)
  }
}
