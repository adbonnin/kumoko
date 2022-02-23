package fr.adbonnin.kumoko.io

import java.io.Closeable

fun Closeable.closeQuietly() {
    IOUtils.closeQuietly(this)
}