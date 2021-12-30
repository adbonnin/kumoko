package fr.adbonnin.kumoko.page;

import java.io.IOException;
import java.util.List;

public interface Page {

    List<Page> scrap(PageItemVisitor visitor) throws IOException;
}
