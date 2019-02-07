import io.reactivex.Single
import java.lang.NullPointerException

// if we get an error and another is thrown in do error, what happens?

fun main() {
//    Single.just(null)
//        .subscribe()

    Single.error<Int>(NullPointerException("asdfasdf"))
        .doOnError { throw IllegalThreadStateException("idk") }
        .subscribe({}, {
            println(it)
        })

    // both errors come out in a CompositeException in this case
}
