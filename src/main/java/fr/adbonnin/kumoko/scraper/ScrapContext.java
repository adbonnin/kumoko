package fr.adbonnin.kumoko.scraper;

import fr.adbonnin.kumoko.Page;
import fr.adbonnin.kumoko.ScrapPageContext;

import java.util.Iterator;
import java.util.LinkedList;

public class ScrapContext implements Iterator<ScrapContext.Node> {

    private final LinkedList<Node> queue = new LinkedList<>();

    private boolean terminated = false;

    public void enqueue(Page page, Iterator<Page> linkedTo) {
        queue.add(new Node(page, linkedTo));
    }

    public Node dequeue() {
        return queue.removeFirst();
    }

    public void terminate() {
        this.terminated = true;
    }

    @Override
    public boolean hasNext() {
        final boolean endReached = terminated || queue.isEmpty();
        return !endReached;
    }

    @Override
    public Node next() {
        return dequeue();
    }

    public class Node implements Iterator<ScrapPageContext> {

        private final Page page;

        private final Iterator<Page> linkedTo;

        private boolean skipped = false;

        public Node(Page page, Iterator<Page> linkedTo) {
            this.page = page;
            this.linkedTo = linkedTo;
        }

        public Page getPage() {
            return page;
        }

        public void terminate() {
            ScrapContext.this.terminate();
        }

        public void skipSiblings() {
            this.skipped = true;
        }

        @Override
        public boolean hasNext() {
            final boolean endReached = terminated || skipped || !linkedTo.hasNext();
            return !endReached;
        }

        @Override
        public ScrapPageContext next() {
            return linkedTo.next();
        }
    }
}
