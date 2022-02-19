package fr.adbonnin.kumoko.io

import spock.lang.Specification

class IOUtilsSpec extends Specification {

    void "should close closeable quietly"() {
        given:
        def closeable = Mock(Closeable)

        when:
        IOUtils.closeQuietly(closeable)

        then:
        1 * closeable.close() >> { throw new IOException() }

        and:
        notThrown(IOException)
    }
}
