package fr.adbonnin.kumoko;

import org.apache.hc.client5.http.classic.HttpClient;

import java.io.IOException;
import java.util.Set;

public interface Page {

    Set<Page> scrap(HttpClient httpClient, PageVisitor visitor) throws IOException;
}
