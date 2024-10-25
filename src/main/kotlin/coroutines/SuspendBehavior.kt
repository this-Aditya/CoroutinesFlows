package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    scope.launch {
        println("In Main Block")
        someHeavyWork(scope)
        println("Out main block")
    }
    Thread.sleep(4000)
    println("Work done")
}


suspend fun someHeavyWork(scope1: CoroutineScope) {
    scope1.launch {
        println("Started heavy work")
        delay(1000)
        println("Mid of heavy work")
        delay(2000)
        println("Finished heavy work")
    }
}