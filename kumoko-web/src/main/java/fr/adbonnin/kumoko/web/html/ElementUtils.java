package fr.adbonnin.kumoko.web.html;

import fr.adbonnin.kumoko.base.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class ElementUtils {

    public static Element appendChildren(Element element, Iterable<? extends Node> children) {

        for (Node child : children) {
            element.appendChild(child);
        }

        return element;
    }

    public static String absUrl(Element element) {
        final String urlAttribute = HtmlUtils.getUrlAttribute(element.nodeName());
        return urlAttribute == null ? StringUtils.EMPTY : element.absUrl(urlAttribute);
    }

    public static String lines(Element element) {
        final LinesNodeVisitor visitor = new LinesNodeVisitor();
        NodeTraversor.traverse(visitor, element);
        return visitor.text();
    }

    /**
     * @see <a href="https://stackoverflow.com/a/17989379">How do I preserve line breaks when using jsoup to convert html to plain text?</a>
     */
    private static class LinesNodeVisitor implements NodeVisitor {

        private final StringBuilder buffer = new StringBuilder();

        private boolean isNewLine = true;

        public String text() {
            return buffer.toString().trim();
        }

        @Override
        public void head(Node node, int depth) {
            if (node instanceof TextNode) {
                final TextNode textNode = (TextNode) node;

                final String text = textNode.text()
                        .replace(StringUtils.NO_BREAK_SPACE, ' ')
                        .trim();

                if (!text.isEmpty()) {
                    buffer.append(text);
                    isNewLine = false;
                }
            }
            else if (node instanceof Element) {
                final Element element = (Element) node;

                if (!isNewLine) {
                    if (element.isBlock() || HtmlUtils.LINE_BREAK_TAG.equals(element.tagName())) {
                        buffer.append("\n");
                        isNewLine = true;
                    }
                }
            }
        }

        @Override
        public void tail(Node node, int depth) {
        }
    }

    private ElementUtils() { /* Cannot be instantiated */ }
}
