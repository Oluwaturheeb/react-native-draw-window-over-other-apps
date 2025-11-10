package com.drawoverotherapps

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import com.facebook.react.ReactApplication
import com.facebook.react.ReactRootView
import kotlin.math.abs

class OverlayService : Service() {

    private var windowManager: WindowManager? = null
    private var reactRootView: ReactRootView? = null
    private var params: WindowManager.LayoutParams? = null

    // Drag handling
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var isDragging = false

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val componentName = intent?.getStringExtra("componentName")
        val props = intent?.getBundleExtra("props")

        val app = application as ReactApplication
        val reactInstanceManager = app.reactNativeHost.reactInstanceManager

        reactRootView = ReactRootView(this).apply {
            startReactApplication(reactInstanceManager, componentName, props)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        val layoutFlag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            // Start as not focusable (doesn't block touches outside)
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_SPLIT_TOUCH or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 0
            y = 100
        }

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager?.addView(reactRootView, params)

        setupTouchHandling()

        return START_STICKY
    }

    /**
     * Makes the overlay draggable while keeping React Native touch handling.
     * Dynamically toggles focusability so touches work properly inside RN views.
     */
    private fun setupTouchHandling() {
        reactRootView?.setOnTouchListener { _, event ->
            val layoutParams = params ?: return@setOnTouchListener false

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Make the window focusable so React Native can receive touches
                    if (layoutParams.flags and WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE != 0) {
                        layoutParams.flags =
                            layoutParams.flags and WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.inv()
                        windowManager?.updateViewLayout(reactRootView, layoutParams)
                    }

                    initialX = layoutParams.x
                    initialY = layoutParams.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isDragging = false
                    false // Let React Native handle click/touch first
                }

                MotionEvent.ACTION_MOVE -> {
                    val xDiff = event.rawX - initialTouchX
                    val yDiff = event.rawY - initialTouchY

                    if (!isDragging && (abs(xDiff) > 20 || abs(yDiff) > 20)) {
                        isDragging = true
                    }

                    if (isDragging) {
                        layoutParams.x = (initialX + xDiff).toInt()
                        layoutParams.y = (initialY + yDiff).toInt()
                        windowManager?.updateViewLayout(reactRootView, layoutParams)
                        true
                    } else {
                        false
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    if (isDragging) {
                        isDragging = false
                    }

                    // Return to non-focusable mode after touch ends
                    layoutParams.flags =
                        layoutParams.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    windowManager?.updateViewLayout(reactRootView, layoutParams)

                    false // Let React Native finish its click handling
                }

                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        reactRootView?.let {
            windowManager?.removeView(it)
            it.unmountReactApplication()
        }
    }
}
