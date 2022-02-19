package fr.adbonnin.kumoko.jdownloader.folderwatch

import com.fasterxml.jackson.databind.ObjectMapper
import fr.adbonnin.kumoko.io.FileUtils
import fr.adbonnin.kumoko.jdownloader.controlling.Priority
import fr.adbonnin.kumoko.jdownloader.extensions.extraction.BooleanStatus
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

class FolderWatchUtilsSpec extends Specification {

    @Shared
    def mapper = new ObjectMapper()

    Path tempDir

    void setup() {
        tempDir = Files.createTempDirectory(FolderWatchUtilsSpec.simpleName).toAbsolutePath()
    }

    void cleanup() {
        FileUtils.deleteRecursively(tempDir)
    }

    void "should create crawl job file"() {
        given:
        def dir = Files.createDirectory(tempDir.resolve('dir')).toFile()

        when:
        def file = FolderWatchUtils.newCrawlJobFile(dir, 'test')

        then:
        file.name == 'test.crawljob'
        file.parentFile == new File(dir, FolderWatchUtils.FOLDER_WATCH_DIRNAME)
    }

    void "should write crawl jobs to file"() {
        given:
        def dir = Files.createDirectory(tempDir.resolve('dir')).toFile()

        when:
        def file = FolderWatchUtils.writeCrawlJobs([crawlJob], dir, 'test')

        then:
        file.name == 'test.crawljob'
        file.parentFile == new File(dir, FolderWatchUtils.FOLDER_WATCH_DIRNAME)

        and:
        def actual = mapper.readTree(file)
        actual == expected

        where:
        crawlJob = new CrawlJobStorable(
                filename: "filename",
                chunks: 1,
                autoConfirm: BooleanStatus.TRUE,
                addOfflineLink: true,

                autoStart: BooleanStatus.TRUE,
                forcedStart: BooleanStatus.TRUE,
                enabled: BooleanStatus.TRUE,
                text: 'text',
                deepAnalyseEnabled: true,
                packageName: 'packageName',
                priority: Priority.HIGHEST,
                extractAfterDownload: BooleanStatus.TRUE,

                downloadFolder: 'downloadFolder',
                extractPasswords: ['extractPasswords'],
                downloadPassword: 'downloadPassword',
                overwritePackagizerEnabled: true,
                setBeforePackagizerEnabled: true,

                type: CrawlJobStorable.JobType.NORMAL,
        )

        expected = mapper.readTree """
        [
          {
            "filename": "filename",
            "chunks": 1,
            "autoConfirm": "TRUE",
            "addOfflineLink": true,
            
            "autoStart": "TRUE",
            "forcedStart": "TRUE",
            "enabled": "TRUE",
            "text": "text",
            "deepAnalyseEnabled": true,
            "packageName": "packageName",
            "priority": "HIGHEST",
            "extractAfterDownload": "TRUE",
            
            "downloadFolder": "downloadFolder",
            "extractPasswords": ["extractPasswords"],
            "downloadPassword": "downloadPassword",
            "overwritePackagizerEnabled": true,
            "setBeforePackagizerEnabled": true,
            
            "type": "NORMAL"
          }
        ]
        """
    }
}