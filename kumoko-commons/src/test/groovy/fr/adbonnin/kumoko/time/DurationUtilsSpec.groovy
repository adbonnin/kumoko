package fr.adbonnin.kumoko.time

import spock.lang.Specification

class DurationUtilsSpec extends Specification {

    void "should create a duration"() {
        def days = 2
        def hours = 3
        def minutes = 4
        def seconds = 5
        def milliseconds = 6

        expect:
        DurationUtils.ofSeconds(seconds) == 5000
        DurationUtils.ofSeconds(seconds, milliseconds) == 5006

        and:
        DurationUtils.ofMinutes(minutes) == 240000
        DurationUtils.ofMinutes(minutes, seconds) == 245000
        DurationUtils.ofMinutes(minutes, seconds, milliseconds) == 245006

        and:
        DurationUtils.ofHours(hours) == 10800000
        DurationUtils.ofHours(hours, minutes) == 11040000
        DurationUtils.ofHours(hours, minutes, seconds) == 11045000
        DurationUtils.ofHours(hours, minutes, seconds, milliseconds) == 11045006

        and:
        DurationUtils.ofDays(days) == 172800000
        DurationUtils.ofDays(days, hours) == 183600000
        DurationUtils.ofDays(days, hours, minutes) == 183840000
        DurationUtils.ofDays(days, hours, minutes, seconds) == 183845000
        DurationUtils.ofDays(days, hours, minutes, seconds, milliseconds) == 183845006
    }
}
