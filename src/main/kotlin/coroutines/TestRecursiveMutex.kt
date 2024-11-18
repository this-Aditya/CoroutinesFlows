package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private val mutexScope: CoroutineScope = CoroutineScope(Dispatchers.Default + Job())
private val mutex = Mutex()

fun main() {
    mutexScope.launch {
        testResA()
    }
    Thread.sleep(2000)
}

suspend fun testResA() {
    println("Trying to acquire resource A")
    mutex.withLock {
        println("Acquired resource A")
        testResB()
    }
    println("Released resource A")
}

suspend fun testResB() {
    println("Trying to acquire resource B")
    mutex.withLock {
        println("Acquired resource B")
    }
    println("Released resource B")
}