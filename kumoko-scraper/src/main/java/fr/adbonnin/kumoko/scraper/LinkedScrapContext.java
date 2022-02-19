package fr.adbonnin.kumoko.scraper;

import fr.adbonnin.kumoko.scraper.collect.LinkedTerminableFiFoQueue;
import fr.adbonnin.kumoko.scraper.collect.TerminableFiFoQueue;
import fr.adbonnin.kumoko.scraper.page.Page;
import fr.adbonnin.kumoko.scraper.page.PageContext;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LinkedScrapContext implements ScrapContext {

    private final TerminableFiFoQueue<PageNode> queue = new LinkedTerminableFiFoQueue<>();

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public void enqueueFirst(List<Page> pages) {
        new PageNode().enqueueFirst(pages);
    }

    @Override
    public PageContext dequeue() {
        return queue.dequeue();
    }

    @Override
    public boolean isTerminated() {
        return queue.isTerminated();
    }

    @Override
    public void terminate() {
        queue.terminate();
    }

    private class PageNode implements PageContext {

        private final Page page;

        private final PageNode parent;

        private final PageNode nextSibling;

        private PageNode firstLink;

        private boolean subtreeSkipped;

        private PageNode() {
            this(null, null, null, false);
        }

        private PageNode(Page page, PageNode parent, PageNode nextSibling, boolean subtreeSkipped) {
            this.page = page;
            this.parent = parent;
            this.nextSibling = nextSibling;
            this.subtreeSkipped = subtreeSkipped;
        }

        @Override
        public Page getPage() {
            return page;
        }

        @Override
        public PageContext getParent() {
            return parent;
        }

        @Override
        public Page getParentPage() {
            return parent == null ? null : parent.getPage();
        }

        @Override
        public boolean isSubtreeSkipped() {
            return subtreeSkipped || isTerminated();
        }

        @Override
        public void skipSubtree() {
            subtreeSkipped = true;

            for (PageContext link : this) {
                link.skipSubtree();
            }
        }

        @Override
        public void skipSiblings() {
            if (parent == null) {
                return;
            }

            for (PageContext sibling : parent) {
                if (sibling != this) {
                    sibling.skipSubtree();
                }
            }
        }

        @Override
        public boolean isTerminated() {
            return LinkedScrapContext.this.isTerminated();
        }

        @Override
        public void terminate() {
            LinkedScrapContext.this.terminate();
        }

        @Override
        public void enqueueFirst(List<Page> pages) {

            if (pages == null || pages.isEmpty()) {
                return;
            }

            final LinkedList<PageNode> pageContexts = new LinkedList<>();
            PageNode nextSibling = firstLink;

            for (int i = pages.size() - 1; i >= 0; i--) {
                final Page page = pages.get(i);
                nextSibling = new PageNode(page, this, nextSibling, subtreeSkipped);
                pageContexts.addFirst(nextSibling);
            }

            firstLink = nextSibling;
            queue.enqueueFirst(pageContexts);
        }

        @Override
        public Iterator<PageContext> iterator() {
            return new Iterator<PageContext>() {
                private PageNode next = firstLink;

                @Override
                public boolean hasNext() {
                    return next != null;
                }

                @Override
                public PageContext next() {
                    final PageNode current = next;
                    next = current.nextSibling;
                    return current;
                }
            };
        }
    }
}
