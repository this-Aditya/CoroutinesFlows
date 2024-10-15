import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
        println("Starting Working on task 1")
        delay(1000)
        println("Completing Working on task 1")
    }
    scope.launch {
        println("Starting Working on task 2")
        throw RuntimeException("Some error")
        delay(500)
        println("Completing Working on task 2")
    }
    scope.launch {
        println("Starting Working on task 3")
        delay(800)
        println("Completing Working on task 3")
    }
    scope.launch {
        println("Starting Working on task 4")
        delay(300)
        println("Completing Working on task 4")
    }
    scope.launch {
        println("Starting Working on task 5")
        delay(600)
        println("Completing Working on task 5")
    }

    Thread.sleep(2000)
}

