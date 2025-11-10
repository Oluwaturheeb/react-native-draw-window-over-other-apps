package com.drawoverotherapps

import android.widget.FrameLayout
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager

class DrawOverOtherAppsViewManager : ViewGroupManager<FrameLayout>() {

  override fun getName(): String = "DrawOverOtherAppsView"

  override fun createViewInstance(reactContext: ThemedReactContext): FrameLayout {
    return FrameLayout(reactContext)
  }

  override fun onDropViewInstance(view: FrameLayout) {
    super.onDropViewInstance(view)
    val module =
            view.context
                    .let { (it as? ThemedReactContext)?.reactApplicationContext }
                    ?.getNativeModule(DrawOverOtherAppsView::class.java)
    module?.hideOverlay()
  }
}
