package fr.adbonnin.kumoko.scraper.collect;

import java.util.Collection;

public interface TerminableFiFoQueue<T> {

    int size();

    void enqueueFirst(Collection<? extends T> values);

    T dequeue();

    boolean isTerminated();

    void terminate();
}
