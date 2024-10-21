package coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val scope = CoroutineScope(Dispatchers.IO + Job())
    scope.launch {
        launch {
            println("Started first coroutine")
            delay(1000)
            println("Completed first coroutine with dispatcher ${tellDispatcher(coroutineContext[CoroutineDispatcher])}")
        }
        delay(1000)
        launch(Dispatchers.Default) {
            println("Started second coroutine")
            delay(1000)
            println("Completed second coroutine with dispatcher: ${tellDispatcher(coroutineContext[CoroutineDispatcher])}")
        }
        delay(1000)
        launch {
            println("Started third coroutine")
            delay(1000)
            println("Completed third coroutine with dispatcher ${tellDispatcher(coroutineContext[CoroutineDispatcher])}")
        }

        println("Parent Dispatcher: ${tellDispatcher(coroutineContext[CoroutineDispatcher])}")
    }
    Thread.sleep(5000)
}

fun tellDispatcher(dispatcher: CoroutineDispatcher?): String {
    return when (dispatcher) {
        Dispatchers.IO -> "Using IO Dispatcher"
        Dispatchers.Default -> "Using Default Dispatcher"
        Dispatchers.Main -> "Using Main Dispatcher"
        else -> "Using a different Dispatcher"
    }
}
