import kotlinx.coroutines.*

// two blocking operations

fun main() = runBlocking {
    withContext(Dispatchers.IO) {
        launch {
            while(true) {
                println(readLine())
            }
        }
        launch {
            repeat(100) {
                println("b")
                delay(500)
            }
        }
    }
    println("done")
}
