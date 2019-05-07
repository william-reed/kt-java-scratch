fun main() {
    Fuzz(3.0)
}

class Fuzz<T : Number>(init: T) {
    init {
        println(init::class.java)
    }
}
