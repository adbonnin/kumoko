package fr.adbonnin.kumoko.base

import fr.adbonnin.kumoko.http.URIUtils
import java.nio.charset.Charset

fun String.removeBefore(searchStr: String, ignoreCase: Boolean = false, fromIndex: Int = 0, include: Boolean = false): String {
    return StringUtils.removeBefore(this, searchStr, ignoreCase, fromIndex, include)
}

fun String.removeBeforeLast(searchStr: String, ignoreCase: Boolean = false, fromIndex: Int = lastIndex, include: Boolean = false): String {
    return StringUtils.removeBeforeLast(this, searchStr, ignoreCase, fromIndex, include)
}

fun String.removeAfter(searchStr: String, ignoreCase: Boolean = false, fromIndex: Int = 0, include: Boolean = false): String {
    return StringUtils.removeAfter(this, searchStr, ignoreCase, fromIndex, include)
}

fun String.removeAfterLast(searchStr: String, ignoreCase: Boolean = false, fromIndex: Int = lastIndex, include: Boolean = false): String {
    return StringUtils.removeAfterLast(this, searchStr, ignoreCase, fromIndex, include)
}

fun String.indexOfIgnoreCase(str: String, fromIndex: Int = 0): Int {
    return StringUtils.indexOfIgnoreCase(this, str, fromIndex)
}

fun String.indexOf(str: String, ignoreCase: Boolean, fromIndex: Int = 0): Int {
    return StringUtils.indexOf(this, str, ignoreCase, fromIndex)
}

fun String.lastIndexOfIgnoreCase(str: String, fromIndex: Int = lastIndex): Int {
    return StringUtils.lastIndexOfIgnoreCase(this, str, fromIndex)
}

fun String.lastIndexOf(str: String, ignoreCase: Boolean, fromIndex: Int = lastIndex): Int {
    return StringUtils.lastIndexOf(this, str, ignoreCase, fromIndex)
}

fun String.encodeUri(): String {
    return URIUtils.encodeUri(this)
}

fun String.encodeUri(charset: Charset): String {
    return URIUtils.encodeUri(this, charset)
}

fun String.decodeUri(): String {
    return URIUtils.decode(this)
}

fun String.decodeUri(charset: Charset): String {
    return URIUtils.decode(this, charset)
}