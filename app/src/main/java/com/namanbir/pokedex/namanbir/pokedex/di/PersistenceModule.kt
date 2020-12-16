

package com.namanbir.pokedex.namanbir.pokedex.di

import android.app.Application
import androidx.room.Room
import com.skydoves.pokedex.persistence.AppDatabase
import com.skydoves.pokedex.persistence.PokemonDao
import com.skydoves.pokedex.persistence.PokemonInfoDao
import com.skydoves.pokedex.persistence.TypeResponseConverter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  @Provides
  @Singleton
  fun provideMoshi(): Moshi {
    return Moshi.Builder().build()
  }

  @Provides
  @Singleton
  fun provideAppDatabase(
    application: Application,
    typeResponseConverter: TypeResponseConverter
  ): AppDatabase {
    return Room
      .databaseBuilder(application, AppDatabase::class.java, "Pokedex.db")
      .fallbackToDestructiveMigration()
      .addTypeConverter(typeResponseConverter)
      .build()
  }

  @Provides
  @Singleton
  fun providePokemonDao(appDatabase: AppDatabase): PokemonDao {
    return appDatabase.pokemonDao()
  }

  @Provides
  @Singleton
  fun providePokemonInfoDao(appDatabase: AppDatabase): PokemonInfoDao {
    return appDatabase.pokemonInfoDao()
  }

  @Provides
  @Singleton
  fun provideTypeResponseConverter(moshi: Moshi): TypeResponseConverter {
    return TypeResponseConverter(moshi)
  }
}
