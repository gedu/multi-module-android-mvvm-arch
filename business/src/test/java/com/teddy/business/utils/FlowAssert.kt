package com.teddy.business.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect


@ExperimentalCoroutinesApi
suspend fun <T> Flow<T>.test(timeoutMs: Long = 1000L, validate: suspend FlowAssert<T>.() -> Unit) {
    coroutineScope {
        val events = Channel<Event<T>>(Channel.UNLIMITED)
        val collectJob = launch {
            val terminalEvent = try {
                collect { item ->
                    events.send(Event.Item(item))
                }
                Event.Complete
            } catch (_: CancellationException) {
                null
            } catch (t: Throwable) {
                Event.Error(t)
            }
            if (terminalEvent != null) {
                events.send(terminalEvent)
            }
            events.close()
        }
        val flowAssert = FlowAssert(events, collectJob, timeoutMs)
        val ensureConsumed = try {
            flowAssert.validate()
            true
        } catch (e: CancellationException) {
            if (e !== ignoreRemainingEventsException) {
                throw e
            }
            false
        }
        if (ensureConsumed) {
            flowAssert.expectNoMoreEvents()
        }
    }
}

private val ignoreRemainingEventsException = CancellationException("Ignore remaining events")

internal sealed class Event<out T> {
    object Complete : Event<Nothing>()
    data class Error(val throwable: Throwable) : Event<Nothing>()
    data class Item<T>(val item: T) : Event<T>()
}

class FlowAssert<T> internal constructor(
    private val events: Channel<Event<T>>,
    private val collectJob: Job,
    private val timeoutMs: Long
) {
    private suspend fun <T> withTimeout(body: suspend () -> T): T {
        return if (timeoutMs == 0L) {
            body()
        } else {
            kotlinx.coroutines.withTimeout(timeoutMs) {
                body()
            }
        }
    }

    fun cancel() {
        collectJob.cancel()
    }

    fun cancelAndIgnoreRemainingEvents(): Nothing {
        cancel()
        throw ignoreRemainingEventsException
    }

    fun expectNoEvents() {
        val event = events.poll()
        if (event != null) {
            throw AssertionError("Expected no events but found $event")
        }
    }

    suspend fun expectNoMoreEvents() {
        val event = withTimeout {
            events.receiveOrNull()
        }
        if (event != null) {
            throw AssertionError("Expected no more events but found $event")
        }
    }

    suspend fun expectItem(): T {
        val event = withTimeout {
            events.receive()
        }
        if (event !is Event.Item<T>) {
            throw AssertionError("Expected item but was $event")
        }
        return event.item
    }

    suspend fun expectComplete() {
        val event = withTimeout {
            events.receive()
        }
        if (event != Event.Complete) {
            throw AssertionError("Expected complete but was $event")
        }
    }

    suspend fun expectError(): Throwable {
        val event = withTimeout {
            events.receive()
        }
        if (event !is Event.Error) {
            throw AssertionError("Expected error but was $event")
        }
        return event.throwable
    }
}