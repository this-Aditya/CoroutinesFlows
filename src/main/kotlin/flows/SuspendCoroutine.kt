package flows

import flows.callbacking.CallbackApi
import flows.callbacking.DummyCallback
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    val api = CallbackApi()
    val value = connectService(api)
    println("Is service connected? $value")
}

suspend fun connectService(api: CallbackApi): String = coroutineScope {

    val value = suspendCoroutine { continuation ->
        val callback = object : DummyCallback {
            override fun onUpdate(value: Int) {
                println("Now continuing")
                continuation.resume("Value is $value")
            }

            override fun onError(message: String?) {
                println("OnError: $message")
            }

            override fun onComplete() {
                println("OnComplete")
            }
        }
        println("Registering callback ")
        if (!api.registerCallback(callback)) {
            throw IllegalStateException("Callback can't be registered!")
        }
    }
    println("Waiting for value: $value")
    value
}