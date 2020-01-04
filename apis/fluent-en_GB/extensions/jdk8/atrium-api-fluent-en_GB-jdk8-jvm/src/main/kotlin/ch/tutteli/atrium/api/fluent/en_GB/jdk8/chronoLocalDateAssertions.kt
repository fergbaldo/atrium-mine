@file:Suppress("JAVA_MODULE_DOES_NOT_READ_UNNAMED_MODULE" /* TODO remove once https://youtrack.jetbrains.com/issue/KT-35343 is fixed */)

package ch.tutteli.atrium.api.fluent.en_GB.jdk8

import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.builders.ExpectImpl
import ch.tutteli.atrium.domain.builders.chronoLocalDate
import java.time.chrono.ChronoLocalDate

/**
 * Expects that the subject of the assertion (a [ChronoLocalDate])
 * is before the [expected] [ChronoLocalDate].
 *
 * @return This assertion container to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 *
 * @since 0.9.0
 */
//TODO #289 should accept ChronoLocalDate, same same but different for other methods
fun <T : ChronoLocalDate> Expect<T>.isBefore(expected: T): Expect<T> =
    addAssertion(ExpectImpl.chronoLocalDate.isBefore(this, expected))

/**
 * Expects that the subject of the assertion (a [ChronoLocalDate])
 * is before or equals the [expected] [ChronoLocalDate].
 *
 * @return This assertion container to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 *
 * @since 0.9.0
 */
fun <T : ChronoLocalDate> Expect<T>.isBeforeOrEquals(expected: T): Expect<T> =
    addAssertion(ExpectImpl.chronoLocalDate.isBeforeOrEquals(this, expected))


/**
 * Expects that the subject of the assertion (a [ChronoLocalDate])
 * is after the [expected] [ChronoLocalDate].
 *
 * @return This assertion container to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 *
 * @since 0.9.0
 */
fun <T : ChronoLocalDate> Expect<T>.isAfter(expected: T): Expect<T> =
    addAssertion(ExpectImpl.chronoLocalDate.isAfter(this, expected))
