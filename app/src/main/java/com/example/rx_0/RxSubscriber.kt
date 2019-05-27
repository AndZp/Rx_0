package com.example.rx_0

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

fun getObservableJust(): Observable<String> {
    return Observable.just("Test: getObservableJust date [${System.currentTimeMillis().toHumanReadableTime()}]")
}

fun getObservableCallable(): Observable<String> {
    return Observable.fromCallable { "Test: getObservableCallable date [${System.currentTimeMillis().toHumanReadableTime()}]" }
}

fun getObservableFrom(): Observable<String> {
    return Observable.fromIterable(0..10)
        .map { index -> ("Test: getObservableJust [$index]") }
}


fun getObservableCreate(): Observable<String> {
    return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<String> ->
        emitter.onNext("Test: getObservableCreate onNext 1")
        emitter.onNext("Test: getObservableCreate  onNext 2")
        emitter.onComplete()
    })
}

fun testObserver() {
    var disposable1: Disposable? = null

    val observable = getObservableCallable()


    observable
        .subscribe(object : Observer<String> {
            override fun onComplete() {
                Log.d("testObserver", "onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                disposable1 = d
                Log.d("testObserver", "onSubscribe")
            }

            override fun onNext(msg: String) {
                Log.d("testObserver", "onNext msg[$msg]")
            }

            override fun onError(e: Throwable) {
                Log.e("testObserver", "onError", e)
            }
        })



    observable
        .subscribeBy {
            Log.d("testKotlinObserver", "onNext msg[$it]")
        }


    val compositeDisposable = CompositeDisposable()
    val disposable2 = observable
        .subscribeBy(
            onNext = { msg -> Log.d("MinCorrectObserver", "onNext msg[$msg]") },
            onError = { error -> Log.e("MinCorrectObserver", "onError", error) })

    compositeDisposable.addAll(disposable1, disposable2)


    compositeDisposable.dispose()
    // ==
    disposable1?.dispose()
    disposable2.dispose()
}


fun testKotlinMinCorrectObserver() {
    val disposable = getObservableJust()
        .subscribeBy(
            onNext = { msg -> Log.d("MinCorrectObserver", "onNext msg[$msg]") },
            onError = { error -> Log.e("MinCorrectObserver", "onError", error) })

    disposable.dispose()
}

