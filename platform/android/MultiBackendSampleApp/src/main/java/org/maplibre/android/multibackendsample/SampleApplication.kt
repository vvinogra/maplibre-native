package org.maplibre.android.multibackendsample

import android.app.Application
import org.maplibre.android.MapLibre
import org.maplibre.android.RenderingEngine
import org.maplibre.android.WellKnownTileServer

/**
 * Applies the persisted renderer choice before the native library is loaded,
 * then initializes MapLibre. [RenderingEngine.setCurrentType] only takes
 * effect if it runs before the first call that triggers native library
 * loading, so it must happen here rather than in the activity.
 */
class SampleApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    RenderingEngine.setCurrentType(EnginePreference.load(this))
    MapLibre.getInstance(applicationContext, null, WellKnownTileServer.MapLibre)
  }
}
