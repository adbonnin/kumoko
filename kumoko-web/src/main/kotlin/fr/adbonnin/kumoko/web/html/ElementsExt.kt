package fr.adbonnin.kumoko.web.html

import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.util.stream.Stream

fun Elements.removeFist(): Element {
    return ElementsUtils.removeFist(this)
}

fun Elements.removeLast(): Element {
    return ElementsUtils.removeLast(this)
}

fun Elements.nextElements(search: Element): Stream<Element> {
    return ElementsUtils.nextElements(this, search)
}

fun Elements.nextElements(index: Int): Stream<Element> {
    return ElementsUtils.nextElements(this, index)
}

fun Elements.absUrls(attributeKey: String): Stream<String> {
    return ElementsUtils.absUrls(this, attributeKey)
}

fun Elements.absUrls(): Stream<String> {
    return ElementsUtils.absUrls(this)
}

fun Elements.texts(): Stream<String> {
    return ElementsUtils.texts(this)
}
