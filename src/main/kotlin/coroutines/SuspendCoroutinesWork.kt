package coroutines

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface DummyCallback {
    fun onResponse(result: String)
}

fun registerCallback(callback: DummyCallback) {
    Thread {
        Thread.sleep(5000)
        callback.onResponse("This is the result string")
    }.start()
}

fun main() = runBlocking {
    val result = fetchResult()
    println("Result is $result")
}

suspend fun fetchResult(): String {
    println("Fetching result")
    val result = suspendCoroutine<String> { continuation ->
        val callback = object : DummyCallback {
            override fun onResponse(result: String) {
                continuation.resume(result)
            }
        }
        registerCallback(callback)
    }
    println("Fetched result")
    return result
}