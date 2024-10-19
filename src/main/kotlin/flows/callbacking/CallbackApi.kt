package flows.callbacking

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CallbackApi {
    private var callback: DummyCallback? = null

     fun registerCallback(callback: DummyCallback): Boolean {
        this.callback = callback
        startSendingUpdates()
        return true
    }

    fun unregisterCallback() {
        callback = null
    }

    private fun startSendingUpdates() {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
            try {
                for (i in 1..6) {
                    if (i == 1) {
                        println("processing sending updates")
                        delay(3000)
                        println("Sending update")
                        callback?.onUpdate(i)
                        break
                    }
                    callback?.onUpdate(i)
                    delay(1000)
                }
                callback?.onComplete()
            } catch (e: Exception) {
                println("Error: ${e.message}")
                callback?.onError(e.message)
            }
        }
    }

}