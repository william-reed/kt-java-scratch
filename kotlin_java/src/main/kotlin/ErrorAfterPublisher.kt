import io.reactivex.Observable
import io.reactivex.ObservableOperator
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.ReplaySubject


fun main() {
    val sub = ReplaySubject.create<Int>()
    sub.onNext(1)
    sub.onNext(2)
    sub.onNext(3)
    var first = true

    sub.map {
        if (it == 2 && first) {
            first = false
            throw IllegalStateException("wee woo")
        }
        it
    }
//        .retry(2)
        .ignoreErrors { println("ignoring error $it") }
        .subscribe({
            println("got $it")
        }, {
            println("error")
        })
}

//fun <T> Observable<T>.ignoreErrors(errorHandler: (Throwable) -> Unit) = lift(OperatorSuppressError(errorHandler))
fun <T> Observable<T>.ignoreErrors(errorHandler: (Throwable) -> Unit): Observable<T> =
    retryWhen { errors ->
        errors
            .doOnNext { errorHandler(it) }
            .map { 0 }
    }


class OperatorSuppressError<T>(private val errorHandler: (Throwable) -> Unit) : ObservableOperator<T, T> {

    override fun apply(downstream: Observer<in T>): Observer<in T> {
        return object : Observer<T> {

            override fun onNext(t: T) {
                downstream.onNext(t)
            }

            override fun onError(e: Throwable) {
                errorHandler(e)

                // don't move it down stream
            }

            override fun onComplete() {
                downstream.onComplete()

            }

            override fun onSubscribe(d: Disposable) {
                downstream.onSubscribe(d)
            }
        }
    }
}
