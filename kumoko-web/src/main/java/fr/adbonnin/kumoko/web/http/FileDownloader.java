package fr.adbonnin.kumoko.web.http;

import fr.adbonnin.kumoko.io.FileUtils;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.io.*;
import java.util.function.Consumer;

public class FileDownloader {

    public File downloadToDir(File dir, String path, String emptyFilename, RedownloadMode redownloadMode, Consumer<HttpClientResponseHandler<Void>> consumer) throws IOException {
        final String filename = HttpUtils.toFilename(path, emptyFilename);
        return download(new File(dir, filename), redownloadMode, consumer);
    }

    public File download(File file, RedownloadMode redownloadMode, Consumer<HttpClientResponseHandler<Void>> consumer) throws IOException {

        final File downloadFile;
        if (!file.exists()) {
            downloadFile = file;
        }
        else if (RedownloadMode.WRITE_TO_NON_EXISTING_FILE.equals(redownloadMode)) {
            downloadFile = newNonExistentFile(file);
        }
        else if (!file.isFile()) {
            throw new IOException("Output file is a directory");
        }
        else if (RedownloadMode.OVERWRITE_EXISTING_FILE.equals(redownloadMode)) {
            downloadFile = file;
        }
        else {
            return file;
        }

        try (FileOutputStream fos = new FileOutputStream(downloadFile);
             OutputStream bos = new BufferedOutputStream(fos)) {
            consumer.accept(new DownloadResponseHandler(bos));
            return downloadFile;
        }
    }

    protected File newNonExistentFile(File file) {
        return FileUtils.newNonExistentFile(file);
    }
}