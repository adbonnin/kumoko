package fr.adbonnin.kumoko.web.html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.stream.Stream;

public class ElementsUtils {

    public static Element removeFist(Elements elements) {
        return elements.remove(0);
    }

    public static Element removeLast(Elements elements) {
        return elements.remove(elements.size() - 1);
    }

    public static Stream<Element> nextElements(Elements elements, Element search) {
        final int index = elements.indexOf(search);
        return nextElements(elements, index);
    }

    public static Stream<Element> nextElements(Elements elements, int index) {
        final boolean isIndexOutOfBounds = index == -1 || index >= elements.size();
        return isIndexOutOfBounds ? Stream.empty() : elements.stream().skip(index + 1);
    }

    public static Stream<String> absUrls(Elements elements, String attributeKey) {
        return elements.stream().map(element -> element.absUrl(attributeKey));
    }

    public static Stream<String> absUrls(Elements elements) {
        return elements.stream().map(ElementUtils::absUrl);
    }

    public static Stream<String> texts(Elements elements) {
        return elements.stream().map(Element::text);
    }

    private ElementsUtils() { /* Cannot be instantiated */ }
}
