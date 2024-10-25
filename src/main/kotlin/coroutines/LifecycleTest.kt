package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    var inner: Job? = null
    val outer = scope.launch {
        println("In the start of outer scope")
        delay(1000)
        println("Some processing done for outer scope")
        delay(2000)
        println("Now launching the inner scope")
        inner = scope.launch {
            println("In the start of inner scope")
            delay(1000)
            println("Some processing done for inner scope")
            delay(1000)
            println("In the mid of the inner scope")
            delay(2000)
            println("Completed inner scope")
        }
        println("After launching the inner scope")
        delay(1000)
        println("Completed outer scope")
    }
    println("Blocking main")
    Thread.sleep(5000)
    println("Completion status: Outer: ${outer.isCompleted} || Inner: ${inner?.isCompleted}") // true, false
    Thread.sleep(4000)
    println("Completion status: Outer: ${outer.isCompleted} || Inner: ${inner?.isCompleted}") // true, true
    println("End")
}