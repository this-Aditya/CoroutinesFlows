package channel.dummy_callbacks

import java.util.Timer
import java.util.TimerTask

fun main() {
    val api = DummyCallbackApi()
    api.registerCallback(object : DummyCallbackApi.Callback {
        override fun onUpdate(data: String) {
            println("Received: $data")
        }
    }, 1000)

    // Stop sending updates after 10 seconds
    Timer().schedule(object : TimerTask() {
        override fun run() {
            api.unregisterCallback()
            println("Stopped sending updates.")
        }
    }, 10000)
}
