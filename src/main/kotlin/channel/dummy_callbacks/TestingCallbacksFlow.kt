package channel.dummy_callbacks

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

private val callbackJob = Job()
private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    println("ExceptionHandler got $exception")
}
private val callbackScope = CoroutineScope(exceptionHandler + callbackJob + Dispatchers.Default)

private var callbackFlow: Flow<String>? = null

fun main() {
    val first = callbackScope.launch {
        getCallbackFlow().collect {
            println(
                "Collected: $it"
            )
        }
    }

    Thread.sleep(10000)
    println("Connected new one")
    val second = callbackScope.launch {
        getCallbackFlow().collect {
            println("Data: $it")
        }
    }

    Thread.sleep(10000)
    first.cancel()
    println("Finalizing")
    Thread.sleep(10000)
    second.cancel()
    println("Finalized")

}

fun getCallbackFlow(): Flow<String> {
//    if (callbackFlow != null) return callbackFlow!!
    println("Creating callback")
    val api = DummyCallbackApi()
    var callback: DummyCallbackApi.Callback? = null
    callbackFlow = callbackFlow {
        callback = createCallback()
        println("CP 1")
        api.registerCallback(callback, 2000)
        println("CP 2")
        awaitClose {
            println("CP 3")
            api.unregisterCallback()
            println("CP 4")
        }
        println("CP 5")
    }
    println("CP: 6")
    return callbackFlow!!
}

fun ProducerScope<String>.createCallback() = object : DummyCallbackApi.Callback {
    override fun onUpdate(data: String) {
        trySendBlocking("Received: $data")
    }
}
