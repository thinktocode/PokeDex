

package com.namanbir.pokedex.namanbir.pokedex.ui.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.skydoves.pokedex.R
import com.skydoves.pokedex.base.DataBindingActivity
import com.skydoves.pokedex.databinding.ActivityDetailBinding
import com.skydoves.pokedex.extensions.argument
import com.skydoves.pokedex.extensions.onTransformationEndContainerApplyParams
import com.skydoves.pokedex.model.Pokemon
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : DataBindingActivity() {

  @Inject
  lateinit var detailViewModelFactory: DetailViewModel.AssistedFactory

  @VisibleForTesting
  val viewModel: DetailViewModel by viewModels {
    DetailViewModel.provideFactory(detailViewModelFactory, pokemonItem.name)
  }

  private val binding: ActivityDetailBinding by binding(R.layout.activity_detail)
  private val pokemonItem: Pokemon by argument(EXTRA_POKEMON)

  override fun onCreate(savedInstanceState: Bundle?) {
    onTransformationEndContainerApplyParams()
    super.onCreate(savedInstanceState)
    binding.apply {
      lifecycleOwner = this@DetailActivity
      pokemon = pokemonItem
      vm = viewModel
    }
  }

  companion object {
    @VisibleForTesting
    const val EXTRA_POKEMON = "EXTRA_POKEMON"

    fun startActivity(transformationLayout: TransformationLayout, pokemon: Pokemon) {
      val context = transformationLayout.context
      if (context is Activity) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(EXTRA_POKEMON, pokemon)
        TransformationCompat.startActivity(transformationLayout, intent)
      }
    }
  }
}
