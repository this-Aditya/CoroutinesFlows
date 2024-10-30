package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    val scope = CoroutineScope(Dispatchers.Default + Job())
    var job: Job? = null
    var result: Result<String>? = null

    job = scope.launch {
        result = runCatching {
            println("Starting the task")
            delay(5000)
            "Completed task"
        }
    }

    scope.launch {
        delay(3000)
        println("Now cancelling the job")
        job.cancel()
    }

    job.join()
    result?.let {
        println("Result is $it \nisFailure: ${it.isFailure} \n isSuccess: ${it.isSuccess}")
    }
    Unit
}