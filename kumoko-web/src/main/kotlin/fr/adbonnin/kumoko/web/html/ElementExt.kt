package fr.adbonnin.kumoko.web.html

import org.jsoup.nodes.Element
import org.jsoup.nodes.Node

fun Element.appendChildren(children: Iterable<Node>): Element {
    return ElementUtils.appendChildren(this, children)
}

fun Element.absUrl(): String {
    return ElementUtils.absUrl(this)
}

fun Element.lines(): String {
    return ElementUtils.lines(this)
}
