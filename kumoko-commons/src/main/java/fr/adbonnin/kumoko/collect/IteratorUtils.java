package fr.adbonnin.kumoko.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public class IteratorUtils {

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> iterator) {
        final ArrayList<E> list = new ArrayList<>();
        IteratorUtils.addAll(list, iterator);
        return list;
    }

    public static <E> boolean addAll(Collection<E> addTo, Iterator<? extends E> iterator) {
        Objects.requireNonNull(addTo);
        Objects.requireNonNull(iterator);

        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified = addTo.add(iterator.next()) || wasModified;
        }

        return wasModified;
    }

    public static <E> Iterator<E> filter(Iterator<? extends E> iterator, Predicate<? super E> predicate) {
        Objects.requireNonNull(iterator);
        Objects.requireNonNull(predicate);

        return new AbstractIterator<E>() {
            @Override
            protected E computeNext() {
                while (iterator.hasNext()) {
                    final E element = iterator.next();
                    if (predicate.test(element)) {
                        return element;
                    }
                }
                return endOfData();
            }
        };
    }

    private IteratorUtils() { /* Cannot be instantiated */ }
}
