package flows.callbacking

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CallbackApi {
    private var callback: DummyCallback? = null

    suspend fun registerCallback(callback: DummyCallback) {
        this.callback = callback
        startSendingUpdates()
    }

    fun unregisterCallback() {
        callback = null
    }

    private suspend fun startSendingUpdates() {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
            try {
                for (i in 1..6) {
                    callback?.onUpdate(i)
                    delay(1000)
                }
                callback?.onComplete()
            } catch (e: Exception) {
                println("Error: ${e.message}")
                callback?.onError(e.message)
            }
        }
        delay(4000)
        job.cancel()
    }

}