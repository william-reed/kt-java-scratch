import io.reactivex.Single

// exception in doOnSuccess goes into error handler?

fun main() {
    val single0 = Single.just(1)

    single0.doOnSuccess { throw IllegalStateException() }
        .subscribe({ println("no error") }, { println("error ${it.localizedMessage}") })
}
