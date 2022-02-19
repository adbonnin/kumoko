package fr.adbonnin.kumoko.scraper.fixtures

import fr.adbonnin.kumoko.scraper.page.Page
import fr.adbonnin.kumoko.scraper.page.PageItemVisitor

class SimplePage implements Page {

    List<Page> links = new ArrayList<>()

    @Override
    List<Page> scrap(PageItemVisitor visitor) throws IOException {
        return links
    }
}
