package fr.adbonnin.kumoko.scraper;

import fr.adbonnin.kumoko.scraper.page.Page;
import fr.adbonnin.kumoko.scraper.page.PageContext;

import java.util.List;

public interface ScrapContext {

    int size();

    void enqueueFirst(List<Page> pages);

    PageContext dequeue();

    boolean isTerminated();

    void terminate();
}
