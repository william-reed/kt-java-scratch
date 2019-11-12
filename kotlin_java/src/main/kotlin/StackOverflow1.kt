import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

// https://stackoverflow.com/questions/56473668/if-you-pass-around-a-stateful-object-between-reactive-lambda-constructs-do-you-n

fun main() {
    var count = 0

    Flowable.range(0, 1000)
        // splits the stream into "parallel pipelines"
        .parallel()
        .runOn(Schedulers.computation())
        .doAfterNext { count += 1 }
        // merge back into a single pipeline
        .sequential()
        .subscribe()

    println(count)
}
