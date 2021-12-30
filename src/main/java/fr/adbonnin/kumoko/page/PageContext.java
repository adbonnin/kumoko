package fr.adbonnin.kumoko.page;

import java.util.List;

public interface PageContext extends Iterable<PageContext> {

    Page getPage();

    PageContext getParent();

    Page getParentPage();

    boolean isSubtreeSkipped();

    void skipSubtree();

    void skipSiblings();

    boolean isTerminated();

    void terminate();

    void enqueueFirst(List<Page> pages);
}
