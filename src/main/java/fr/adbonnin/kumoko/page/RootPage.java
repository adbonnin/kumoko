package fr.adbonnin.kumoko.page;

import fr.adbonnin.kumoko.Page;
import fr.adbonnin.kumoko.PageVisitor;
import org.apache.hc.client5.http.classic.HttpClient;

import java.util.Collections;
import java.util.Set;

public class RootPage implements Page {

    public static RootPage INSTANCE = new RootPage();

    @Override
    public Set<Page> scrap(HttpClient httpClient, PageVisitor visitor) {
        return Collections.emptySet();
    }
}
