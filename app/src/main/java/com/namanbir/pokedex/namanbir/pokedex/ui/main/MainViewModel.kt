

package com.namanbir.pokedex.namanbir.pokedex.ui.main

import androidx.annotation.MainThread
import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.skydoves.pokedex.base.LiveCoroutinesViewModel
import com.skydoves.pokedex.model.Pokemon
import com.skydoves.pokedex.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(
  private val mainRepository: MainRepository,
  @Assisted private val savedStateHandle: SavedStateHandle
) : LiveCoroutinesViewModel() {

  private val pokemonFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
  val pokemonListLiveData: LiveData<List<Pokemon>>

  private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
  val toastLiveData: LiveData<String> get() = _toastLiveData

  val isLoading: ObservableBoolean = ObservableBoolean(false)

  init {
    Timber.d("init MainViewModel")

    pokemonListLiveData = pokemonFetchingIndex.asLiveData().switchMap {
      isLoading.set(true)
      launchOnViewModelScope {
        this.mainRepository.fetchPokemonList(
          page = it,
          onSuccess = { isLoading.set(false) },
          onError = { _toastLiveData.postValue(it) }
        ).asLiveData()
      }
    }
  }

  @MainThread
  fun fetchPokemonList() {
    pokemonFetchingIndex.value++
  }
}
