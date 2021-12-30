package fr.adbonnin.kumoko.fixtures

import fr.adbonnin.kumoko.page.Page
import fr.adbonnin.kumoko.page.PageItemVisitor

class SimplePage implements Page {

    List<Page> links = new ArrayList<>()

    @Override
    List<Page> scrap(PageItemVisitor visitor) throws IOException {
        return links
    }
}
