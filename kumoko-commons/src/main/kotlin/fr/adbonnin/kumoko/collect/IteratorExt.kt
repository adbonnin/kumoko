package fr.adbonnin.kumoko.collect

import java.util.function.Predicate

fun <T> Iterator<T>.filter(predicate: Predicate<T>) {
    IteratorUtils.filter(this, predicate)
}