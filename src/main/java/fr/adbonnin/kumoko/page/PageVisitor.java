package fr.adbonnin.kumoko.page;

import java.io.IOException;

public interface PageVisitor extends PageItemVisitor {

    void preVisitPage(PageContext context) throws IOException;

    void visitPageFailed(PageContext context, IOException exc) throws IOException;

    void postVisitPage(PageContext context) throws IOException;
}
