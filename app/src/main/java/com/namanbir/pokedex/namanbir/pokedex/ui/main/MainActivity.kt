

package com.namanbir.pokedex.namanbir.pokedex.ui.main

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.skydoves.pokedex.R
import com.skydoves.pokedex.base.DataBindingActivity
import com.skydoves.pokedex.databinding.ActivityMainBinding
import com.skydoves.pokedex.ui.adapter.PokemonAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DataBindingActivity() {

  @VisibleForTesting val viewModel: MainViewModel by viewModels()
  private val binding: ActivityMainBinding by binding(R.layout.activity_main)

  override fun onCreate(savedInstanceState: Bundle?) {
    onTransformationStartContainer()
    super.onCreate(savedInstanceState)
    binding.apply {
      lifecycleOwner = this@MainActivity
      adapter = PokemonAdapter()
      vm = viewModel
    }
  }
}
