package org.maplibre.android.multibackendsample

import android.content.Context
import org.maplibre.android.RenderingEngine

/**
 * Persists the user's chosen [RenderingEngine.Type] across process restarts.
 *
 * The multiBackend flavor locks the native library selection for the process
 * lifetime (see [RenderingEngine]), so switching backends means restarting
 * the app; this is where the choice survives that restart.
 */
object EnginePreference {

  private const val PREFS_NAME = "multi_backend_sample"
  private const val KEY_ENGINE_TYPE = "engine_type"

  fun load(context: Context): RenderingEngine.Type {
    val stored = prefs(context).getString(KEY_ENGINE_TYPE, null) ?: return RenderingEngine.Type.OPENGL
    return runCatching { RenderingEngine.Type.valueOf(stored) }.getOrDefault(RenderingEngine.Type.OPENGL)
  }

  fun save(context: Context, type: RenderingEngine.Type) {
    prefs(context).edit().putString(KEY_ENGINE_TYPE, type.name).apply()
  }

  private fun prefs(context: Context) =
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
}
