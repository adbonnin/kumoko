package fr.adbonnin.kumoko.io;

import fr.adbonnin.kumoko.base.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtils {

    public static final char EXTENSION_SEPARATOR = '.';

    public static final char[] FILENAME_RESERVED_CHARACTERS = {
            '"',  // 0x34 double quote
            '*',  // 0x42 asterisk
            '/',  // 0x47 forward slash
            ':',  // 0x58 colon
            '<',  // 0x60 less than
            '>',  // 0x62 greater than
            '?',  // 0x63 question mark
            '\\', // 0x92 backslash
            '|'   // 0x124 vertical bar or pipe
    };

    public static int indexOfExtension(String name) {
        return name.lastIndexOf(EXTENSION_SEPARATOR);
    }

    public static String basename(String name) {
        final int index = indexOfExtension(name);
        return index == -1 ? name : name.substring(0, index);
    }

    public static String extension(String name) {
        final int index = indexOfExtension(name);
        return index == -1 ? StringUtils.EMPTY : name.substring(index + 1);
    }

    public static boolean hasExtension(String name) {
        final int index = indexOfExtension(name);
        return index != -1;
    }

    public static File newNonExistentCleanedFile(File parent, String filename, String emptyFilename) {
        final File cleanedFile = newCleanedFile(parent, filename, emptyFilename);
        return newNonExistentFile(cleanedFile);
    }

    public static File newNonExistentCleanedFile(File parent, String filename, String emptyFilename, int retryLimit) {
        final File cleanedFile = newCleanedFile(parent, filename, emptyFilename);
        return newNonExistentFile(cleanedFile, retryLimit);
    }

    public static File newNonExistentCleanedFile(File parent, String filename, String emptyFilename, int retryLimit, NonExistentFileBuilder builder) {
        final File cleanedFile = newCleanedFile(parent, filename, emptyFilename);
        return newNonExistentFile(cleanedFile, retryLimit, builder);
    }

    public static File newNonExistentFile(File file) {
        return newNonExistentFile(file, Integer.MAX_VALUE);
    }

    public static File newNonExistentFile(File file, int retryLimit) {
        return newNonExistentFile(file, retryLimit, SimpleNonExistentFilenameBuilder.INSTANCE);
    }

    public static File newNonExistentFile(File file, int retryLimit, NonExistentFileBuilder builder) {

        if (!file.exists()) {
            return file;
        }

        for (int retryCount = 0; retryCount < retryLimit; retryCount++) {
            final File newFile = builder.buildNonExistentFile(file, retryCount);
            if (!newFile.exists()) {
                return newFile;
            }
        }

        throw new NonExistentFileLimitExceededException("Retry limit has been exceeded; file: " + tryCanonicalPath(file));
    }

    public static File newCleanedFile(File parent, String filename, String emptyFilename) {
        final String cleanedFilename = cleanFilename(filename, emptyFilename);
        return new File(parent, cleanedFilename);
    }

    public static File toCleanedFile(File file, String emptyFilename) {
        final String filename = file.getName();
        final String cleanedFilename = cleanFilename(filename, emptyFilename);
        return filename.equals(cleanedFilename) ? file : new File(file.getParent(), cleanedFilename);
    }

    /**
     * Remove reserved and illegals file name characters from {@code name}.
     *
     * @see <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/aa365247(v=vs.85).aspx">Microsoft: Naming Files, Paths, and Namespaces</a>
     */
    public static String cleanFilename(String name, String emptyFilename) {
        final int len = name.length();

        final StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            final char c = name.charAt(i);
            if (c <= 31) {
                continue;
            }

            if (isNameReservedCharacter(c)) {
                continue;
            }

            sb.append(c);
        }

        int start = 0;
        int end = sb.length();

        while ((start < end) && sb.charAt(start) <= ' ') {
            ++start;
        }

        while (start < end) {
            final char c = sb.charAt(end - 1);
            if (c <= ' ' || c == EXTENSION_SEPARATOR) {
                --end;
            }
            else {
                break;
            }
        }

        if (start == end) {
            return emptyFilename;
        }

        return (end - start) == len ? name : sb.substring(start, end);
    }

    private static boolean isNameReservedCharacter(char c) {

        for (char reserved : FILENAME_RESERVED_CHARACTERS) {
            if (c == reserved) {
                return true;
            }
            else if (c < reserved) {
                return false;
            }
        }

        return false;
    }

    /**
     * Return {@code file.getCanonicalPath} or {@code file.getPath()} if an exception is thrown.
     * If {@code file} is null, {@code "null"} is returned.
     */
    public static String tryCanonicalPath(File file) {

        if (file == null) {
            return null;
        }

        try {
            return file.getCanonicalPath();
        }
        catch (IOException e) {
            return file.getPath();
        }
    }

    public static void createDir(File dir) throws IOException {
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw new IOException("File exists but is not a directory; directory: " + tryCanonicalPath(dir));
            }
        }
        else {
            if (!dir.mkdirs()) {
                // Double-check that the directory wasn't created.
                if (!dir.isDirectory()) {
                    throw new IOException("Unable to create directory; directory: " + tryCanonicalPath(dir));
                }
            }
        }
    }

    public static boolean deleteRecursively(Path path) throws IOException {

        if (Files.notExists(path)) {
            return false;
        }

        Files.walkFileTree(path, DELETE_RECURSIVELY_VISITOR);
        return true;
    }

    private static final FileVisitor<Path> DELETE_RECURSIVELY_VISITOR = new SimpleFileVisitor<Path>() {

        @Override
        public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {

            if (attrs.isSymbolicLink() || attrs.isOther()) {
                Files.delete(dir);
                return FileVisitResult.SKIP_SUBTREE;
            }

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

            if (exc != null) {
                throw exc;
            }

            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    };

    private FileUtils() { /* Cannot be instantiated */ }
}