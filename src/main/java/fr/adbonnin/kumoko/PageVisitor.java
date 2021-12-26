package fr.adbonnin.kumoko;

import java.io.IOException;

public interface PageVisitor {

    void preVisitPage(ScrapPageContext context) throws IOException;

    void visitItem(ScrapPageContext context, Object item) throws IOException;

    void visitPageFailed(ScrapPageContext context, IOException exc) throws IOException;

    void postVisitPage(ScrapPageContext context) throws IOException;
}
