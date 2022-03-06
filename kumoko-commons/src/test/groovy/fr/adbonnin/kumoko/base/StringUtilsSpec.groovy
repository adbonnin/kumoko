package fr.adbonnin.kumoko.base


import spock.lang.Specification

class StringUtilsSpec extends Specification {

    static TEST_STR = '01ab02ab03'

    void "should remove the characters before the index of a searched string"() {
        expect:
        StringUtils.removeBefore(str, searchStr, ignoreCase, fromIndex, include) == expectedStr1
        StringUtils.removeBefore(str, searchStr, ignoreCase, fromIndex) == expectedStr2
        StringUtils.removeBefore(str, searchStr, ignoreCase) == expectedStr3
        StringUtils.removeBefore(str, searchStr) == expectedStr4

        where:
        searchStr | fromIndex || expectedStr1 | expectedStr2 | expectedStr3 | expectedStr4
        'ab'      | 0         || 'ab02ab03'   | '02ab03'     | '02ab03'     | '02ab03'
        'ab'      | 4         || 'ab03'       | '03'         | '02ab03'     | '02ab03'
        'ab'      | 8         || TEST_STR     | TEST_STR     | '02ab03'     | '02ab03'

        'AB'      | 0         || 'ab02ab03'   | '02ab03'     | '02ab03'     | TEST_STR
        'AB'      | 4         || 'ab03'       | '03'         | '02ab03'     | TEST_STR
        'AB'      | 8         || TEST_STR     | TEST_STR     | '02ab03'     | TEST_STR

        str = TEST_STR
        ignoreCase = true
        include = true
    }

    void "should remove the characters before the last index index of a searched string"() {
        expect:
        StringUtils.removeBeforeLast(str, searchStr, ignoreCase, fromIndex, include) == expectedStr1
        StringUtils.removeBeforeLast(str, searchStr, ignoreCase, fromIndex) == expectedStr2
        StringUtils.removeBeforeLast(str, searchStr, ignoreCase) == expectedStr3
        StringUtils.removeBeforeLast(str, searchStr) == expectedStr4

        where:
        searchStr | fromIndex || expectedStr1 | expectedStr2 | expectedStr3 | expectedStr4
        'ab'      | 8         || 'ab03'       | '03'         | '03'         | '03'
        'ab'      | 4         || 'ab02ab03'   | '02ab03'     | '03'         | '03'
        'ab'      | 0         || TEST_STR     | TEST_STR     | '03'         | '03'

        'AB'      | 8         || 'ab03'       | '03'         | '03'         | TEST_STR
        'AB'      | 4         || 'ab02ab03'   | '02ab03'     | '03'         | TEST_STR
        'AB'      | 0         || TEST_STR     | TEST_STR     | '03'         | TEST_STR

        str = TEST_STR
        ignoreCase = true
        include = true
    }

    void "should remove the characters after the index of a searched string"() {
        expect:
        StringUtils.removeAfter(TEST_STR, searchStr, ignoreCase, fromIndex, include) == expectedStr1
        StringUtils.removeAfter(TEST_STR, searchStr, ignoreCase, fromIndex) == expectedStr2
        StringUtils.removeAfter(TEST_STR, searchStr, ignoreCase) == expectedStr3
        StringUtils.removeAfter(TEST_STR, searchStr) == expectedStr4

        where:
        searchStr | fromIndex || expectedStr1 | expectedStr2 | expectedStr3 | expectedStr4
        'ab'      | 0         || '01ab'       | '01'         | '01'         | '01'
        'ab'      | 4         || '01ab02ab'   | '01ab02'     | '01'         | '01'
        'ab'      | 8         || TEST_STR     | TEST_STR     | '01'         | '01'

        'AB'      | 0         || '01ab'       | '01'         | '01'         | TEST_STR
        'AB'      | 4         || '01ab02ab'   | '01ab02'     | '01'         | TEST_STR
        'AB'      | 8         || TEST_STR     | TEST_STR     | '01'         | TEST_STR

        str = TEST_STR
        ignoreCase = true
        include = true
    }

    void "should remove the characters after the last index of a searched string"() {
        expect:
        StringUtils.removeAfterLast(TEST_STR, searchStr, ignoreCase, fromIndex, include) == expectedStr1
        StringUtils.removeAfterLast(TEST_STR, searchStr, ignoreCase, fromIndex) == expectedStr2
        StringUtils.removeAfterLast(TEST_STR, searchStr, ignoreCase) == expectedStr3
        StringUtils.removeAfterLast(TEST_STR, searchStr) == expectedStr4

        where:
        searchStr | fromIndex || expectedStr1 | expectedStr2 | expectedStr3 | expectedStr4
        'ab'      | 8         || '01ab02ab'   | '01ab02'     | '01ab02'     | '01ab02'
        'ab'      | 4         || '01ab'       | '01'         | '01ab02'     | '01ab02'
        'ab'      | 0         || TEST_STR     | TEST_STR     | '01ab02'     | '01ab02'

        'AB'      | 8         || '01ab02ab'   | '01ab02'     | '01ab02'     | TEST_STR
        'AB'      | 4         || '01ab'       | '01'         | '01ab02'     | TEST_STR
        'AB'      | 0         || TEST_STR     | TEST_STR     | '01ab02'     | TEST_STR

        str = TEST_STR
        ignoreCase = true
        include = true
    }

    void "should find the index of a string"() {
        expect:
        StringUtils.indexOf(str, searchStr, ignoreCase, fromIndex) == expectedIndex1
        StringUtils.indexOf(str, searchStr, ignoreCase) == expectedIndex2
        StringUtils.indexOfIgnoreCase(str, searchStr, fromIndex) == expectedIndex3
        StringUtils.indexOfIgnoreCase(str, searchStr) == expectedIndex4

        where:
        searchStr | fromIndex || expectedIndex1 | expectedIndex2 | expectedIndex3 | expectedIndex4
        'ab'      | 0         || 2              | 2              | 2              | 2
        'ab'      | 4         || 6              | 2              | 6              | 2
        'ab'      | 8         || -1             | 2              | -1             | 2

        'AB'      | 0         || -1             | -1             | 2              | 2
        'AB'      | 4         || -1             | -1             | 6              | 2
        'AB'      | 8         || -1             | -1             | -1             | 2

        str = TEST_STR
        ignoreCase = false
    }

    void "should find the last index of a string"() {
        expect:
        StringUtils.lastIndexOf(str, searchStr, ignoreCase, fromIndex) == expectedIndex1
        StringUtils.lastIndexOf(str, searchStr, ignoreCase) == expectedIndex2
        StringUtils.lastIndexOfIgnoreCase(str, searchStr, fromIndex) == expectedIndex3
        StringUtils.lastIndexOfIgnoreCase(str, searchStr) == expectedIndex4

        where:
        searchStr | fromIndex || expectedIndex1 | expectedIndex2 | expectedIndex3 | expectedIndex4
        'ab'      | 8         || 6              | 6              | 6              | 6
        'ab'      | 4         || 2              | 6              | 2              | 6
        'ab'      | 0         || -1             | 6              | -1             | 6

        'AB'      | 8         || -1             | -1             | 6              | 6
        'AB'      | 4         || -1             | -1             | 2              | 6
        'AB'      | 0         || -1             | -1             | -1             | 6

        str = TEST_STR
        ignoreCase = false
    }

    void "should strip accents"() {
        expect:
        StringUtils.stripAccents(str) == expectedStr

        where:
        str                                          || expectedStr
        null                                         || null
        ''                                           || ''
        'a-b'                                        || 'a-b'

        '\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u0104' || 'AAAAAAA'
        '\u00C7\u0106'                               || 'CC'
        '\u00C8\u00C9\u00CA\u00CB'                   || 'EEEE'
        '\u00CC\u00CD\u00CE\u00CF'                   || 'IIII'
        '\u0141'                                     || 'L'
        '\u00D1\u0143'                               || 'NN'
        '\u00D2\u00D3\u00D4\u00D5\u00D6'             || 'OOOOO'
        '\u00D9\u00DA\u00DB\u00DC'                   || 'UUUU'
        '\u015A'                                     || 'S'
        '\u00DD'                                     || 'Y'
        '\u017B\u0179'                               || 'ZZ'

        '\u0105'                                     || 'a'
        '\u0107'                                     || 'c'
        '\u0142'                                     || 'l'
        '\u0144'                                     || 'n'
        '\u00F3'                                     || 'o'
        '\u015B'                                     || 's'
        '\u017C\u017A'                               || 'zz'
    }
}
