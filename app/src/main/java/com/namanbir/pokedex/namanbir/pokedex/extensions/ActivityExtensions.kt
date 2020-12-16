

package com.namanbir.pokedex.namanbir.pokedex.extensions

import android.os.Parcelable
import androidx.activity.ComponentActivity
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer

/** apply [TransformationLayout.Params] to an activity. */
fun ComponentActivity.onTransformationEndContainerApplyParams() {
  onTransformationEndContainer(intent.getParcelableExtra("com.skydoves.transformationlayout"))
}

/** initialize a parcelable argument lazily. */
fun <T : Parcelable> ComponentActivity.argument(key: String): Lazy<T> {
  return lazy { requireNotNull(intent.getParcelableExtra<T>(key)) }
}
