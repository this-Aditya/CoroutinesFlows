package coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext

class SharedResource {
    private val mutex = Mutex()
    private var counter = 0

    suspend fun increment() {
        mutex.withLock {
            counter++
            println("Incremented: $counter")
        }
    }

    suspend fun decrement() {
        mutex.withLock {
            counter--
            println("Decremented: $counter")
        }
    }

    suspend fun reset() {
        mutex.withLock {
            counter = 0
            println("Reset: $counter")
        }
    }
}

//fun main() = runBlocking {
//    val sharedResource = SharedResource()
//    val jobs = List(100) {
//        launch {
//            sharedResource.increment()
//            sharedResource.decrement()
//            sharedResource.reset()
//        }
//    }
//    jobs.joinAll()
//}


fun main() = runBlocking {
    val test = Test()
    val scope1 = CoroutineScope(Dispatchers.Default + Job() + CoroutineName("scope1"))
    scope1.launch {
        test.resA(this.coroutineContext)
        test.resB(this.coroutineContext)
    }

    val scope2 = CoroutineScope(Dispatchers.Default + Job() + CoroutineName("scope2"))
    scope2.launch {
        test.resB(this.coroutineContext)
        test.resA(this.coroutineContext)
    }
    delay(2000)
    Unit
}


class Test {
    val mutexA = Mutex()
    val mutexB = Mutex()
    private var counter = 0

    suspend fun resA(context: CoroutineContext) {
        println("Coroutine: ${context[CoroutineName]?.name} tried to acquire resource A. Is it available?: ${!mutexA.isLocked}")
        mutexA.withLock {
            println("Coroutine: ${context[CoroutineName]?.name} acquired resource A")
            counter++
            println("Incremented Counter: $counter")
        }
    }

    suspend fun resB(context: CoroutineContext) {
        println("Coroutine: ${context[CoroutineName]?.name} tried to acquire resource B. Is it available ${!mutexB.isLocked}")
        mutexB.withLock {
            println("Coroutine: ${context[CoroutineName]?.name} acquired resource B")
            counter--
            println("Decremented Counter: $counter")
        }
    }
}