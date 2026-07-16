package org.maplibre.android.multibackendsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import org.maplibre.android.RenderingEngine
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.OnMapReadyCallback
import org.maplibre.android.maps.Style
import android.widget.TextView

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

  private lateinit var mapView: MapView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)


    mapView = findViewById(R.id.mapView)
    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync(this)

    val currentType = RenderingEngine.getCurrentType()
    findViewById<TextView>(R.id.currentEngineLabel).text =
      getString(R.string.current_engine, currentType.name)

    val switchButton = findViewById<MaterialButton>(R.id.switchBackendButton)

    val targetType = otherType(currentType)

    switchButton.text = getString(R.string.switch_backend_button, targetType.name)
    switchButton.setOnClickListener {
      EnginePreference.save(this, targetType)
      restartApp()
    }
  }

  override fun onMapReady(map: MapLibreMap) {
    map.setStyle(Style.Builder().fromUri(MAP_STYLE_URL))
  }

  private fun otherType(type: RenderingEngine.Type): RenderingEngine.Type =
    if (type == RenderingEngine.Type.OPENGL) RenderingEngine.Type.VULKAN else RenderingEngine.Type.OPENGL

  private fun restartApp() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    Runtime.getRuntime().exit(0)
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  companion object {
    private const val MAP_STYLE_URL = "https://demotiles.maplibre.org/style.json"
  }
}
