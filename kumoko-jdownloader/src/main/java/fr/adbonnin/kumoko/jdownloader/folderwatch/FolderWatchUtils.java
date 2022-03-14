package fr.adbonnin.kumoko.jdownloader.folderwatch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.adbonnin.kumoko.base.StringUtils;
import fr.adbonnin.kumoko.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FolderWatchUtils {

    public static final String CRAWL_JOB_EXTENSION = "crawljob";

    public static final String FOLDER_WATCH_DIRNAME = "folderwatch";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static File writeCrawlJobs(Iterable<? extends CrawlJobStorable> crawlJobs, File jdownloaderDir, String basename) throws IOException {
        final File crawlJobFile = newCrawlJobFile(jdownloaderDir, basename);
        FileUtils.createParentDirectories(crawlJobFile);
        writeValue(crawlJobs, crawlJobFile);
        return crawlJobFile;
    }

    public static File newCrawlJobFile(File jdownloaderDir, String basename) {
        final File folderWatchDir = new File(jdownloaderDir, FOLDER_WATCH_DIRNAME);
        final String crawlJobFilename = basename + FileUtils.EXTENSION_SEPARATOR + CRAWL_JOB_EXTENSION;
        return FileUtils.newNonExistentCleanedFile(folderWatchDir, crawlJobFilename, StringUtils.EMPTY);
    }

    public static void writeValue(Object value, File file) throws IOException {
        OBJECT_MAPPER.writeValue(file, value);
    }

    public static String writeValueAsString(Object value) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(value);
    }

    private FolderWatchUtils() { /* Cannot be instantiated */ }
}
