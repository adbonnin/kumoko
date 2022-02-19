package fr.adbonnin.kumoko.scraper.page;

import java.io.IOException;
import java.util.List;

public interface Page {

    List<Page> scrap(PageItemVisitor visitor) throws IOException;
}
