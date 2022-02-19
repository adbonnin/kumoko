package fr.adbonnin.kumoko.io;

import java.io.File;

@FunctionalInterface
public interface NonExistentFileBuilder {

    File buildNonExistentFile(File file, int retryCount);
}

