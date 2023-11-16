package org.example.sources;

import java.util.*;
import java.util.function.Consumer;

public class ClockShop
        implements Iterable<IClock> {
    protected Collection<IClock> catalog = new ArrayList<>();
    private final Collection<IOserver> observers = new ArrayList<>();
    private final Map<String, Integer> tableOfNumberOccurrences = new HashMap<>();
    public ClockShop() {
    }
    // Observation
    private void events() {
        observers.forEach(
                o -> o.event(this)
        );
    }
    public void sub(IOserver obs) { observers.add(obs); }

    // Edit
    public void addClock(IClock clock) {
        this.catalog.add(clock);
        Integer val = this.tableOfNumberOccurrences.get(clock.getName());
        this.tableOfNumberOccurrences.put(clock.getName(), val == null ? 1 : val + 1);
        events();
    }
    public void addAllClocks(Collection<IClock> collection) {
        this.catalog.addAll(collection);
        collection.forEach(clock -> {
            Integer val = this.tableOfNumberOccurrences.get(clock.getName());
            this.tableOfNumberOccurrences.put(clock.getName(), val == null ? 1 : val + 1);
        });
        events();
    }

    public void removeClock(IClock clock) {
        this.catalog.remove(clock);
        Integer val = this.tableOfNumberOccurrences.get(clock.getName());
        this.tableOfNumberOccurrences.put(clock.getName(), val - 1);
        events();
    }

    public void clear() {
        this.catalog.clear();
        this.tableOfNumberOccurrences.clear();
        events();
    }
    // Modify
    public void setAllTime(Time time) {
        this.catalog.forEach(clock -> clock.setStartTime(time));
        events();
    }

    public void moveAllTime(Time time) {
        this.catalog.forEach(clock -> clock.timeShift(time));
        events();
    }
    // Process
    public IClock getInfoMaxPrice() {
        return Collections.max(this.catalog, (c1, c2) -> Double.compare(c1.getPrice(), c2.getPrice()));
    }

    public String getMostCommon() {
        if (this.catalog.isEmpty())
            return "н/д";
        Map.Entry<String, Integer> max =
                Collections.max(this.tableOfNumberOccurrences.entrySet(), Map.Entry.comparingByValue());
        return max.getKey();
    }

    public Collection<String> getListOfNames() {
        return new TreeSet<>(this.tableOfNumberOccurrences.keySet());
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