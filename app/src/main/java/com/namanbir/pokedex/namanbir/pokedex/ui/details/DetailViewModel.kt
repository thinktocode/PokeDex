

package com.namanbir.pokedex.namanbir.pokedex.ui.details

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skydoves.pokedex.base.LiveCoroutinesViewModel
import com.skydoves.pokedex.model.PokemonInfo
import com.skydoves.pokedex.repository.DetailRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber

class DetailViewModel @AssistedInject constructor(
  private val detailRepository: DetailRepository,
  @Assisted private val pokemonName: String
) : LiveCoroutinesViewModel() {

  val pokemonInfoLiveData: LiveData<PokemonInfo?>

  private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
  val toastLiveData: LiveData<String> get() = _toastLiveData

  val isLoading: ObservableBoolean = ObservableBoolean(false)

  init {
    Timber.d("init DetailViewModel")

    pokemonInfoLiveData = launchOnViewModelScope {
      isLoading.set(true)
      detailRepository.fetchPokemonInfo(
        name = pokemonName,
        onSuccess = { isLoading.set(false) },
        onError = { _toastLiveData.postValue(it) }
      ).asLiveData()
    }
  }

  @AssistedInject.Factory
  interface AssistedFactory {
    fun create(pokemonName: String): DetailViewModel
  }

  companion object {
    fun provideFactory(
      assistedFactory: AssistedFactory,
      pokemonName: String
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return assistedFactory.create(pokemonName) as T
      }
    }
  }
}
