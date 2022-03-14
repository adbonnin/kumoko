package fr.adbonnin.kumoko.io

import java.io.File

fun File.indexOfExtension(): Int {
    return FileUtils.indexOfExtension(name)
}

fun File.basename(): String {
    return FileUtils.basename(name)
}

fun File.extension(): String {
    return FileUtils.extension(name)
}

fun File.hasExtension(): Boolean {
    return FileUtils.hasExtension(name)
}

fun File.withBasename(basename: String): File {
    return FileUtils.withBasename(this, basename)
}

fun File.newNonExistentCleanedFile(filename: String, emptyFilename: String, retryLimit: Int = Int.MAX_VALUE, builder: NonExistentFileBuilder = SimpleNonExistentFilenameBuilder.INSTANCE): File {
    return FileUtils.newNonExistentCleanedFile(this, filename, emptyFilename, retryLimit, builder)
}

fun File.newCleanedFile(filename: String, emptyFilename: String): File {
    return FileUtils.newCleanedFile(this, filename, emptyFilename)
}

fun File.toCleanedFile(emptyFilename: String): File {
    return FileUtils.toCleanedFile(this, emptyFilename)
}

fun File.tryCanonicalPath(): String {
    return FileUtils.tryCanonicalPath(this)
}

fun File.createDirectory():File {
    return FileUtils.createDirectory(this)
}

fun File.createParentDirectories():File {
    return FileUtils.createParentDirectories(this)
}

fun File.deleteRecursively(): Boolean {
    return FileUtils.deleteRecursively(this)
}