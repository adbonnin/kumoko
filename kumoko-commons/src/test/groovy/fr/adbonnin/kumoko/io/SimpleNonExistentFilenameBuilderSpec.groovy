package fr.adbonnin.kumoko.io

import spock.lang.Specification
import spock.lang.Subject

class SimpleNonExistentFilenameBuilderSpec extends Specification {

    def "should build the file '#name' at retry #retryCount with name '#expected'"() {
        given:
        @Subject def filenameBuilder = SimpleNonExistentFilenameBuilder.INSTANCE

        and:
        def parent = new File("dir")
        def expectedFile = new File(parent, expected)

        def file = Mock(File) {
            getName() >> name
            getParentFile() >> parent
        }

        expect:
        filenameBuilder.buildNonExistentFile(file, retryCount) == expectedFile

        where:
        name      | retryCount || expected
        'foo.bar' | 0          || 'foo (1).bar'
        'foo'     | 1          || 'foo (2)'
        '.bar'    | 2          || '(3).bar'
        ''        | 3          || '(4)'
    }
}
