import io.reactivex.Maybe

// maybe with no emission his onSuccess or onComplete?
fun main() {
    Maybe.empty<String>()
            .subscribe({ println("success") }, { println("error") }, { println("complete") })

    Maybe.just("apple")
        .subscribe({ println("success") }, { println("error") }, { println("complete") })

//    Maybe.just("apple")
}
