package com.example.rx_0

import io.reactivex.*

fun getFlowable():Flowable<String>{
        return Flowable.create(FlowableOnSubscribe { emmiter ->
            emmiter.onNext("1")
            emmiter.onNext("2")
            emmiter.onNext("3")

            emmiter.onError(IllegalArgumentException())
            // OR
            emmiter.onComplete()

        }, BackpressureStrategy.LATEST)
}

fun getCompletable():Completable{
    return Completable.create {
        it.onComplete()
        //or
        it.onError(IllegalArgumentException())
    }
}

fun getMaybe():Maybe<String>{
    return Maybe.create {
        it.onSuccess("Data")
        // or
        it.onComplete()
        //or
        it.onError(IllegalArgumentException())

    }
}


fun getSingle():Single<String>{
    return Single.create {
        it.onSuccess("Data")
        //or
        it.onError(IllegalArgumentException())

    }
}