package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob


private val outerExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
    println("Caught $exception")
}

private val outerScope = CoroutineScope(Dispatchers.Default + outerExceptionHandler + Job())

fun main() {

}