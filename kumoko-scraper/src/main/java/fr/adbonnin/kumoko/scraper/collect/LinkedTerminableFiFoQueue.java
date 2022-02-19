package fr.adbonnin.kumoko.scraper.collect;

import java.util.Collection;
import java.util.LinkedList;

public class LinkedTerminableFiFoQueue<T> implements TerminableFiFoQueue<T> {

    private LinkedList<T> queue = new LinkedList<>();

    @Override
    public int size() {
        return queue == null ? 0 : queue.size();
    }

    @Override
    public void enqueueFirst(Collection<? extends T> values) {
        if (queue != null) {
            queue.addAll(0, values);
        }
    }

    @Override
    public T dequeue() {
        return queue == null ? null : queue.pollFirst();
    }

    @Override
    public boolean isTerminated() {
        return queue == null;
    }

    @Override
    public void terminate() {
        queue = null;
    }
}
