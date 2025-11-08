package com.drawoverotherapps

import android.graphics.Color
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.DrawOverOtherAppsViewManagerInterface
import com.facebook.react.viewmanagers.DrawOverOtherAppsViewManagerDelegate

@ReactModule(name = DrawOverOtherAppsViewManager.NAME)
class DrawOverOtherAppsViewManager : SimpleViewManager<DrawOverOtherAppsView>(),
  DrawOverOtherAppsViewManagerInterface<DrawOverOtherAppsView> {
  private val mDelegate: ViewManagerDelegate<DrawOverOtherAppsView>

  init {
    mDelegate = DrawOverOtherAppsViewManagerDelegate(this)
  }

  override fun getDelegate(): ViewManagerDelegate<DrawOverOtherAppsView>? {
    return mDelegate
  }

  override fun getName(): String {
    return NAME
  }

  public override fun createViewInstance(context: ThemedReactContext): DrawOverOtherAppsView {
    return DrawOverOtherAppsView(context)
  }

  @ReactProp(name = "color")
  override fun setColor(view: DrawOverOtherAppsView?, color: String?) {
    view?.setBackgroundColor(Color.parseColor(color))
  }

  companion object {
    const val NAME = "DrawOverOtherAppsView"
  }
}
