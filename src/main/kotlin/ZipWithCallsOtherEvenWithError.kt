import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import java.lang.Thread.sleep

// does .zipwith call the other observable even if there is an error in the main observable

fun main() {
    val errorObservable = Single.error<String>(IllegalArgumentException())
    val otherObservable = Single.just(1).doOnSubscribe { println("otherObservable called") }

    errorObservable.zipWith(otherObservable)
        .subscribe({
            println("finished succesfully")
        }, {
            println("error! ${it.message}")
        })

    sleep(100)
}
