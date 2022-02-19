package fr.adbonnin.kumoko.io;

import java.io.File;

public class SimpleNonExistentFilenameBuilder implements NonExistentFileBuilder {

    public static final SimpleNonExistentFilenameBuilder INSTANCE = new SimpleNonExistentFilenameBuilder();

    @Override
    public File buildNonExistentFile(File file, int retryCount) {
        final StringBuilder sb = new StringBuilder();
        final String name = file.getName();

        final String basename = FileUtils.basename(name);
        if (!basename.isEmpty()) {
            sb.append(basename).append(' ');
        }

        sb.append('(').append(retryCount + 1).append(')');

        if (FileUtils.hasExtension(name)) {
            sb.append(FileUtils.EXTENSION_SEPARATOR).append(FileUtils.extension(name));
        }

        return new File(file.getParentFile(), sb.toString());
    }
}
