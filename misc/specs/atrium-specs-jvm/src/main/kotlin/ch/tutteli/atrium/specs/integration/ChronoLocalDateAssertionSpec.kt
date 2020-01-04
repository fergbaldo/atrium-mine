package ch.tutteli.atrium.specs.integration

import ch.tutteli.atrium.api.fluent.en_GB.messageContains
import ch.tutteli.atrium.api.fluent.en_GB.toThrow
import ch.tutteli.atrium.api.verbs.internal.expect
import ch.tutteli.atrium.specs.*
import ch.tutteli.atrium.translations.DescriptionDateTimeLikeAssertion
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import java.time.chrono.ChronoLocalDate

abstract class ChronoLocalDateAssertionSpec(
    isBefore: Fun1<ChronoLocalDate, ChronoLocalDate>,
    isBeforeOrEquals: Fun1<ChronoLocalDate, ChronoLocalDate>,
    isAfter: Fun1<ChronoLocalDate, ChronoLocalDate>,
    describePrefix: String = "[Atrium] "
) : Spek({

    val december22 = LocalDate.of(2019, 12, 22)
    val december23 = LocalDate.of(2019, 12, 23)
    val december24 = LocalDate.of(2019, 12, 24)

    include(object : SubjectLessSpec<ChronoLocalDate>(
        describePrefix,
        isBefore.forSubjectLess(december23),
        isBeforeOrEquals.forSubjectLess(december23),
        isAfter.forSubjectLess(december23)
    ) {})

    val isBeforeDescr = DescriptionDateTimeLikeAssertion.IS_BEFORE.getDefault()
    val isBeforeOrEqualsDescr = DescriptionDateTimeLikeAssertion.IS_BEFORE_OR_EQUALS.getDefault()
    val isAfterDescr = DescriptionDateTimeLikeAssertion.IS_AFTER.getDefault()

    val fluent = expect(december23 as ChronoLocalDate)

    describe("$describePrefix subject is $december23") {
        describe("${isBefore.name} ...") {
            val isBeforeFun = isBefore.lambda

            it("... $december22 throws an AssertionError") {
                expect {
                    fluent.isBeforeFun(LocalDate.of(2019, 12, 22))
                }.toThrow<AssertionError> { messageContains("$isBeforeDescr: $december22") }
            }
            it("... $december23 throws an AssertionError") {
                expect {
                    fluent.isBeforeFun(december23)
                }.toThrow<AssertionError> { messageContains("$isBeforeDescr: $december23") }
            }
            it("... $december24 does not throw") {
                fluent.isBeforeFun(LocalDate.of(2019, 12, 24))
            }
        }
        describe("${isBeforeOrEquals.name} ...") {
            val isBeforeOrEqualsFun = isBeforeOrEquals.lambda

            it("... $december22 throws an AssertionError") {
                expect {
                    fluent.isBeforeOrEqualsFun(LocalDate.of(2019, 12, 22))
                }.toThrow<AssertionError> { messageContains("$isBeforeOrEqualsDescr: $december22") }
            }
            it("... $december23 does not throw") {
                fluent.isBeforeOrEqualsFun(december23)
            }
            it("... $december24 does not throw") {
                fluent.isBeforeOrEqualsFun(LocalDate.of(2019, 12, 24))
            }
        }
        describe("${isAfter.name} ...") {
            val isAfterFun = isAfter.lambda

            it("... $december22 does not throw") {
                fluent.isAfterFun(LocalDate.of(2019, 12, 22))
            }
            it("... $december23 throws an AssertionError") {
                expect {
                    fluent.isAfterFun(december23)
                }.toThrow<AssertionError> { messageContains("$isAfterDescr: $december23") }
            }
            it("... $december24 throws an AssertionError") {
                expect {
                    fluent.isAfterFun(LocalDate.of(2019, 12, 24))
                }.toThrow<AssertionError> { messageContains("$isAfterDescr: $december24") }
            }
        }
    }
})
