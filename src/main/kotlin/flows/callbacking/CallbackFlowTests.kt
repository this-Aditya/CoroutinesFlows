package flows.callbacking

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

fun main() = runBlocking<Unit> {
    val api = CallbackApi()

    val flow = callbackFlow<Int> {
        val callback = object : DummyCallback {
            override fun onUpdate(value: Int) {
                trySendBlocking(value)
//                println("Received update: $value")
            }

            override fun onError(message: String?) {
                cancel(CancellationException("Event API error: $message"))
//                println("Error: $message")
            }

            override fun onComplete() {
                println("Completed")
                close()
//                println("Completed")
            }
        }
        println("Registering")
        api.registerCallback(callback)
        awaitClose {
            println("Unregistering callback")
            api.unregisterCallback()
        }
    }


    launch {
        flow.map {
            println("Mapping: $it")
            "Value: $it"
        }.collect {
            println("Received $it")
        }
    }
//    Thread.sleep(12000L)
}

