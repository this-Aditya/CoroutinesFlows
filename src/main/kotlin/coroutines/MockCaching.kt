package coroutines

fun main() {
//    val base = "./src/main/resources/topic/google_sleep_segment_event"
//    val fileBases = mutableListOf<Pair<String, DatacacheGroup>>(
//
//    )

    val pairMap: List<Pair<String, Int>> = listOf(("One" to 1), ("Two" to 2), ("Three" to 3), ("Four" to 4))
    pairMap.none { it.first.also(::println)
        false }
}

data class DataCacheGroup(
    val name: String,
)