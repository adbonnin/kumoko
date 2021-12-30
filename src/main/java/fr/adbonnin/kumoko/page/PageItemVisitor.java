package fr.adbonnin.kumoko.page;

import java.io.IOException;

public interface PageItemVisitor {

    void visitItem(Object item) throws IOException;
}
