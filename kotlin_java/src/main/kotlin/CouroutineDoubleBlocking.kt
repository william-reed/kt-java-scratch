import kotlinx.coroutines.*

// two blocking operations

fun main() = runBlocking {
    withContext(Dispatchers.IO) {
        launch {
            while (true) {
                val input = readLine()
                if (input == "end") break

            }
        }
        launch {
            repeat(10) {
                println("b")
                delay(500)
            }
        }
    }
    println("done")
}
