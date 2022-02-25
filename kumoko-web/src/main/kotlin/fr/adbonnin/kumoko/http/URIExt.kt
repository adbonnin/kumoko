package fr.adbonnin.kumoko.http

fun String.encodeUri(): String {
    return URIUtils.encodeUri(this)
}

fun String.decodeUri(): String {
    return URIUtils.decodeUri(this)
}