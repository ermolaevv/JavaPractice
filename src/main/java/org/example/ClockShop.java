package org.example;

import java.util.*;
import java.util.function.Consumer;

import org.example.IClock;
import org.example.Time;

public class ClockShop
        implements Iterable<IClock> {
    protected Collection<IClock> catalog = new ArrayList<IClock>();
    private final Map<String, Integer> tableOfNumberOccurrences = new HashMap<String, Integer>();

    ClockShop() {
    }

    public IClock getInfoMaxPrice() {
        return Collections.max(this.catalog, (c1, c2) -> Double.compare(c1.getPrice(), c2.getPrice()));
    }

    public void setAllTime(Time time) {
        this.catalog.forEach(clock -> clock.setStartTime(time));
    }

    public void moveAllTime(Time time) {
        this.catalog.forEach(clock -> clock.timeShift(time));
    }

    public String getMostCommon() {
        Map.Entry max = Collections.max(this.tableOfNumberOccurrences.entrySet(), Map.Entry.comparingByValue());
        return (String)max.getKey();
    }

    public void addClock(IClock clock) {
        this.catalog.add(clock);
        Integer val = this.tableOfNumberOccurrences.get(clock.getName());
        this.tableOfNumberOccurrences.put(clock.getName(), val == null ? 1 : val + 1);
    }

    public void addAllClocks(Collection<IClock> collection) {
        this.catalog.addAll(collection);
        collection.forEach(clock -> {
            Integer val = this.tableOfNumberOccurrences.get(clock.getName());
            this.tableOfNumberOccurrences.put(clock.getName(), val == null ? 1 : val + 1);
        });
    }

    public void removeClock(IClock clock) {
        this.catalog.remove(clock);
        Integer val = this.tableOfNumberOccurrences.get(clock.getName());
        this.tableOfNumberOccurrences.put(clock.getName(), val - 1);
    }

    public void clear() {
        this.catalog.clear();
        this.tableOfNumberOccurrences.clear();
    }

    public Collection<String> getListOfNames() {
        return new TreeSet<String>(this.tableOfNumberOccurrences.keySet());
    }

    public String toString() {
        return this.catalog.toString();
    }

    @Override
    public Iterator<IClock> iterator() {
        return this.catalog.iterator();
    }

    @Override
    public void forEach(Consumer<? super IClock> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<IClock> spliterator() {
        return Iterable.super.spliterator();
    }
}