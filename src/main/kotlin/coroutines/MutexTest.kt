package coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class MutexTest {
    val mutex = Mutex()
    val context = Dispatchers.Default
    val scope = CoroutineScope(context + Job() + CoroutineName("test-mutex"))
    suspend fun startTask() {
        withContext(Dispatchers.IO) {
            mutex.withLock {
                println("Starting work-1")
                delay(1000)
                println("Completed work-1")
            }
        }
    }

    suspend fun midTask() {
        withContext(Dispatchers.IO) {
            mutex.withLock {
                println("Starting work-2")
                delay(2000)
                println("Completed work-2")
            }
        }
    }

    suspend fun endTask() {
        withContext(scope.coroutineContext) {
            mutex.withLock {
                println("Starting work-3")
                delay(3000)
                println("Completed work-3")
            }
        }
    }
}

fun main() {
    val test1 = MutexTest()
    val scope = CoroutineScope(Job() + CoroutineName("test-mutex-1"))
    scope.launch {
        launch {
            test1.startTask()
        }
        launch {
            test1.midTask()
        }
        val lastJob = launch {
            test1.endTask()
        }
    }
    Thread.sleep(9000)
}