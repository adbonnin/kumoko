package fr.adbonnin.kumoko;

public interface ScrapPageContext {

    Page getPage();

    Page getParentPage();

    void terminate();

    void skipSiblings();

    void skipSubtree();

    boolean isSubtreeSkipped();
}
