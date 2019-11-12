import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

// https://stackoverflow.com/questions/56596146/retry-buffer-in-rxjava
public class StackOverflow2 {

    public static void main(String[] args) {
        // store any failures that may have occurred
        LinkedBlockingQueue<String> failures = new LinkedBlockingQueue<>();

        toUpload()
                // buffer however you want
                .buffer(5)
                // here is the interesting part
                .flatMap(strings -> {
                    // add any previous failures
                    List<String> prevFailures = new ArrayList<>();
                    failures.drainTo(prevFailures);
                    strings.addAll(prevFailures);

                    return Flowable.just(strings);
                })
                .flatMapCompletable(strings -> {
                    // upload the data
                    return upload(strings).doOnError(throwable -> {
                        // if its an upload failure:
                        failures.addAll(strings);
                    });
                }).subscribe();
    }

    // whatever your source flowable is
    private static Flowable<String> toUpload() {
        return Flowable.fromIterable(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i"));
    }

    // some upload operation
    private static Completable upload(List<String> strings) {
        return Completable.complete();
    }
}
