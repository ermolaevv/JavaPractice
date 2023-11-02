package org.example;

import java.util.ArrayList;
import org.example.ClockFactory;
import org.example.ClockShop;
import org.example.EClockTypes;
import org.example.IClock;
import org.example.Time;

public class Main {
    public static void main(String[] args) throws Exception {
        ClockFactory factory = new ClockFactory();
        ClockShop cs = new ClockShop();

        int SIZE = 10;

        ArrayList<IClock> clocks = new ArrayList<IClock>();
        for (int i = 0; i < 10; ++i) {
            clocks.add(factory.create(EClockTypes.THREE));
        }

        cs.addAllClocks(clocks);

        System.out.println("Before setAllTime");
        cs.forEach(System.out::println);

        cs.setAllTime(new Time(10, 11, 12));

        System.out.println("\nAfter setAllTime");
        cs.forEach(System.out::println);

        System.out.println("\nClock with max price: " + String.valueOf(cs.getInfoMaxPrice()));
        System.out.println("Most common name: " + cs.getMostCommon());
        System.out.println(cs.getListOfNames());

        IClock clock = factory.create(EClockTypes.TWO);

        System.out.println("\nAdd clock " + String.valueOf(clock));
        cs.addClock(clock);
        cs.forEach(System.out::println);

        System.out.println("\nRemove clock " + String.valueOf(clock));
        cs.removeClock(clock);
        cs.forEach(System.out::println);
    }
}