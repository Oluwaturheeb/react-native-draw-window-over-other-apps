package com.drawoverotherapps

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.os.Bundle
import com.facebook.react.bridge.*

class DrawOverOtherAppsView(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String = "DrawOverOtherAppsView"

    // Show overlay
    @ReactMethod
    fun showOverlay(componentName: String, props: ReadableMap?) {
        val context = reactApplicationContext
        if (Settings.canDrawOverlays(context)) {
            val intent = Intent(context, OverlayService::class.java)
            intent.putExtra("componentName", componentName)
            intent.putExtra("props", toBundle(props))
            context.startService(intent)
        } else {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.packageName)
            )
            context.currentActivity?.startActivityForResult(intent, 123)
        }
    }

    // Hide overlay
    @ReactMethod
    fun hideOverlay() {
        val context = reactApplicationContext
        val intent = Intent(context, OverlayService::class.java)
        context.stopService(intent)
    }

    // Open main app from overlay
    @ReactMethod
    fun openApp() {
        val context = reactApplicationContext
        val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        context.startActivity(launchIntent)
    }

    // Convert ReadableMap to Bundle
    private fun toBundle(map: ReadableMap?): Bundle? {
        if (map == null) return null
        val bundle = Bundle()
        val iterator = map.keySetIterator()
        while (iterator.hasNextKey()) {
            val key = iterator.nextKey()
            when (map.getType(key)) {
                ReadableType.Null -> bundle.putString(key, null)
                ReadableType.Boolean -> bundle.putBoolean(key, map.getBoolean(key))
                ReadableType.Number -> bundle.putDouble(key, map.getDouble(key))
                ReadableType.String -> bundle.putString(key, map.getString(key))
                ReadableType.Map -> bundle.putBundle(key, toBundle(map.getMap(key)))
                else -> {}
            }
        }
        return bundle
    }
}
