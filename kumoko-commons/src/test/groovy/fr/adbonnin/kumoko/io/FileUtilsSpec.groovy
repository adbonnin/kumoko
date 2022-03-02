package fr.adbonnin.kumoko.io

import spock.lang.Requires
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import java.util.prefs.Preferences

/**
 * @link https://git.eclipse.org/c/statet/org.eclipse.statet-commons.git/diff/jcommons/org.eclipse.statet.jcommons.util/src/org/eclipse/statet/jcommons/io/FileUtils.java?id=fcedc3a623283cf2e5156bd4fd12302aaff4b36a
 * @link https://git.eclipse.org/c/statet/org.eclipse.statet-commons.git/diff/jcommons/org.eclipse.statet.jcommons.util-tests/src/org/eclipse/statet/jcommons/io/FileUtilsTest.java?id=fcedc3a623283cf2e5156bd4fd12302aaff4b36a
 */
class FileUtilsSpec extends Specification {

    Path tempDir

    void setup() {
        tempDir = Files.createTempDirectory(FileUtilsSpec.simpleName).toAbsolutePath()
    }

    void cleanup() {
        FileUtils.deleteRecursively(tempDir)
    }

    static boolean isRunningAsAdministrator() {
        def preferences = Preferences.systemRoot()
        def name = 'SPOCK_IS_RUNNING_AS_ADMINISTRATOR'

        try {
            preferences.put(name, 'true')
            preferences.remove(name)
            preferences.flush()
            return true
        }
        catch (Exception ignored) {
            return false
        }
    }

    def "should return the name '#name' for basename '#basename' #extensionLabel"() {
        expect:
        FileUtils.indexOfExtension(name) == indexOfExtension
        FileUtils.basename(name) == basename
        FileUtils.extension(name) == extension
        FileUtils.hasExtension(name) == hasExtension

        where:
        name       || indexOfExtension | basename | extension | hasExtension
        ''         || -1               | ''       | ''        | false
        'name'     || -1               | 'name'   | ''        | false
        'name.'    || 4                | 'name'   | ''        | true
        '.ext'     || 0                | ''       | 'ext'     | true
        'name.ext' || 4                | 'name'   | 'ext'     | true

        extensionLabel = hasExtension ? "with extension '$extension' at $indexOfExtension" : 'without extension'
    }

    def "should return a new non existent cleaned file"() {
        given:
        def emptyFilename = 'empty'
        def reservedFilename = "${FileUtils.FILENAME_RESERVED_CHARACTERS[0]}$FileUtils.EXTENSION_SEPARATOR"

        and:
        def cleanedFile = tempDir.resolve(emptyFilename).toFile()
        cleanedFile.write("Exiting file")

        expect:
        cleanedFile.isFile()

        when:
        def file = FileUtils.newNonExistentCleanedFile(tempDir.toFile(), reservedFilename, emptyFilename)

        then:
        file == tempDir.resolve("$emptyFilename (1)").toFile()
    }

    def "should return the same file if the file doesn't exists"() {
        given:
        def file = Mock(File)
        def filenameBuilder = Mock(NonExistentFileBuilder)

        when:
        def result = FileUtils.newNonExistentFile(file, 3, filenameBuilder)

        then:
        1 * file.exists() >> false
        0 * filenameBuilder.buildNonExistentFile(*_)

        and:
        result.is(file)
    }

    def "should return a non existent file if the file already exists"() {
        given:
        def file = Mock(File)
        def existentFile = Mock(File)
        def expectedFile = Mock(File)

        def filenameBuilder = Mock(NonExistentFileBuilder)

        when:
        def result = FileUtils.newNonExistentFile(file, 3, filenameBuilder)

        then:
        1 * file.exists() >> true

        then:
        1 * filenameBuilder.buildNonExistentFile(file, 0) >> existentFile
        1 * existentFile.exists() >> true

        then:
        1 * filenameBuilder.buildNonExistentFile(file, 1) >> expectedFile
        1 * expectedFile.exists() >> false

        and:
        result.is(expectedFile)
    }

    void "should throw an exception when retry limit is reached"() {
        given:
        def file = Mock(File)

        when:
        FileUtils.newNonExistentFile(file, 0)

        then:
        1 * file.exists() >> true
        1 * file.getCanonicalPath() >> 'cp'

        def ex = thrown(NonExistentFileLimitExceededException)
        ex.message == 'Retry limit has been exceeded; file: cp'
    }

    def "should throw an exception if the file to clean is null"() {
        when:
        FileUtils.cleanFilename(null, 'empty')

        then:
        thrown(NullPointerException)
    }

    def "should clear the '#filename' to '#expected'"() {
        expect:
        FileUtils.cleanFilename(filename, 'empty') == expected

        where:
        filename  | expected
        "foo}"    | "foo}"
        " foo"    | "foo"
        "foo."    | "foo"
        "foo "    | "foo"
        "foo>"    | "foo"
        "\01foo"  | "foo"
        " <foo> " | "foo"
        "< foo >" | "foo"
        " . "     | "empty"
    }

    void "should get the canonical path of a file"() {
        def canonicalPath = 'cp'

        given:
        def file = Mock(File.class)

        when:
        def result = FileUtils.tryCanonicalPath(file)

        then:
        1 * file.getCanonicalPath() >> canonicalPath
        0 * file.getPath()

        and:
        result == canonicalPath
    }

    void "should get the path if the canonical path is not available"() {
        def path = 'p'

        given:
        def file = Mock(File.class)

        when:
        def result = FileUtils.tryCanonicalPath(file)

        then:
        1 * file.getCanonicalPath() >> { throw new IOException() }

        then:
        1 * file.getPath() >> path

        and:
        result == path
    }

    void "should create the directory"() {
        given:
        def dir = Mock(File) {
            exists() >> dirExists
            expectedIsDirecory * isDirectory() >> true
            expectedMkdirs * mkdirs() >> true
        }

        when:
        FileUtils.createDir(dir)

        then:
        notThrown(IOException)

        where:
        dirExists || expectedIsDirecory | expectedMkdirs
        true      || 1                  | 0
        false     || 0                  | 1
    }

    void "should throw an exception if the creation of the directory has failed"() {
        given:
        def dir = Mock(File) {
            exists() >> dirExists
            expectedIsDirecory * isDirectory() >> false
            expectedMkdirs * mkdirs() >> false
        }

        when:
        FileUtils.createDir(dir)

        then:
        thrown(IOException)

        where:
        dirExists || expectedIsDirecory | expectedMkdirs
        true      || 1                  | 0
        false     || 1                  | 1
    }

    void "should recursively delete the folder"() {
        given:
        def dir = Files.createDirectory(tempDir.resolve("dir"))
        Files.write(dir.resolve("file"), "Hello".bytes)
        Files.write(dir.resolve("file_2"), "Hello".bytes)

        def subDir = Files.createDirectory(dir.resolve("sub"))
        Files.write(subDir.resolve("file"), "Hello".bytes)

        when:
        def result = FileUtils.deleteRecursively(dir)

        then:
        result
        Files.notExists(dir)
    }

    void "should return false if the file doesn't exists"() {
        given:
        def dir = tempDir.resolve("dir")

        expect:
        Files.notExists(dir)

        when:
        def result = FileUtils.deleteRecursively(dir)

        then:
        !result
        Files.notExists(dir)
    }

    @Requires({ isRunningAsAdministrator() })
    void "should delete recursively the folder with symbolic link file"() {
        given:
        def dir = Files.createDirectory(tempDir.resolve("dir"))
        Files.write(dir.resolve("file"), "Hello".bytes)

        and: "Target file with symbolic link"
        def targetFile = Files.write(tempDir.resolve("target-file"), "Hello".bytes)
        Files.createSymbolicLink(dir.resolve("file-link"), targetFile)

        when:
        def result = FileUtils.deleteRecursively(dir)

        then:
        result
        Files.notExists(dir)

        and: "Target symbolic linked file still exists"
        Files.exists(targetFile)
    }

    void "should delete recursively the folder with file links"() {
        given:
        def dir = Files.createDirectory(tempDir.resolve("dir"))
        Files.write(dir.resolve("file"), "Hello".bytes)

        and: "Target file with link"
        def targetFile = Files.write(tempDir.resolve("target-file"), "Hello".bytes)
        Files.createLink(dir.resolve("file-link"), targetFile)

        when:
        def result = FileUtils.deleteRecursively(dir)

        then:
        result
        Files.notExists(dir)

        and: "Target linked file still exists"
        Files.exists(targetFile)
    }

    @Requires({ isRunningAsAdministrator() })
    void "should delete recursively the folder without deleting folders with symbolic link"() {
        given:
        def dir = Files.createDirectory(tempDir.resolve("dir"))
        Files.write(dir.resolve("file"), "Hello".bytes)

        and: "Target folder with symbolic link"
        def targetDir = Files.createDirectory(tempDir.resolve("target-dir"))
        Files.createSymbolicLink(dir.resolve("dir-link"), targetDir)

        def targetDirFile = Files.write(targetDir.resolve("target-dir-file"), "Hello".bytes)

        when:
        def result = FileUtils.deleteRecursively(dir)

        then:
        result
        Files.notExists(dir)

        and: "Target symbolic folder still exists with its content"
        Files.exists(targetDir)
        Files.exists(targetDirFile)
    }

    @Requires({ os.windows })
    void "should delete recursively the folder without deleting folders created with the Windows junction command"() {
        given:
        def dir = Files.createDirectory(tempDir.resolve("dir"))
        Files.write(dir.resolve("file"), "Hello".bytes)

        and: "Target folder with junction command"
        def targetDir = Files.createDirectory(tempDir.resolve("file"))
        def linkDir = dir.resolve("dir-link")

        new ProcessBuilder("cmd", "/C", "mklink", "/J", linkDir.toString(), targetDir.toString())
                .directory(tempDir.toFile())
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectErrorStream(true)
                .start()
                .waitFor(5, TimeUnit.SECONDS)

        def targetDirFile = Files.write(targetDir.resolve("target-dir-file"), "Hello".bytes)

        expect:
        Files.isDirectory(linkDir)

        when:
        def result = FileUtils.deleteRecursively(dir)

        then:
        result
        Files.notExists(dir)

        and: "Target folder still exists with its content"
        Files.exists(targetDir)
        Files.exists(targetDirFile)
    }
}
