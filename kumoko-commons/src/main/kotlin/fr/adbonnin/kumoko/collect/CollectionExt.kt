package fr.adbonnin.kumoko.collect

fun <T> MutableCollection<T>.addAll(iterator: Iterator<T>): Boolean {
    return IteratorUtils.addAll(this, iterator)
}