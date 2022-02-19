package fr.adbonnin.kumoko.io;

public class IOUtils {

    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (Throwable e) {
                /* Nothing to do */
            }
        }
    }

    private IOUtils() { /* Cannot be instantiated */ }
}
