package com.teddy.business.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws


/**
 * Safely handles observables from LiveData for testing.
 *
 * <a href="https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/androidTest/java/com/example/android/persistence/LiveDataTestUtil.java">Google sample</a>
 *
 * Requires InstantTaskExecutorRule
 */
object LiveDataTestUtil {

    /**
     * Gets the value of a LiveData safely.
     */
    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T? {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data
    }

    fun <T> onFirstValue(liveData: LiveData<T>, onFirst: (T) -> Unit) {

        liveData.observeOnce(Observer {
            onFirst(it)
        })
    }
}

fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}

/**
 * Observes LiveData events until any event fires
 *
 * @param observer Observer for LiveData
 *
 */
fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}