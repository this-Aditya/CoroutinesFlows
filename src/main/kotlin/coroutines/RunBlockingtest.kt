package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)
    println("Started Main")
    launch {
        getResult()
    }
    println("Stopped Main")
}

suspend fun getResult() = coroutineScope {
    println("Scope Started")
    delay(2000)
    println("Scope Stopped")
}