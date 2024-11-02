package channel.dummy_callbacks

import java.util.Timer
import java.util.TimerTask

class DummyCallbackApi {

    interface Callback {
        fun onUpdate(data: String)
    }

    private var timer: Timer? = null

    fun registerCallback(callback: Callback, intervalMillis: Long) {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Simulate some data being generated
                val data = "Update at ${System.currentTimeMillis()}"
                callback.onUpdate(data)
            }
        }, 0, intervalMillis) // Initial delay is 0, interval is specified by caller
    }

    fun unregisterCallback() {
        timer?.cancel()
        timer = null
    }
}

