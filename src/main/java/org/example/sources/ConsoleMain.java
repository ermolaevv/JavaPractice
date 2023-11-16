package org.example.sources;

import java.util.ArrayList;

public class ConsoleMain {
    public static void main(String[] args) {
        ClockShop cs = new ClockShop();
        ClockFactory cf = new ClockFactory();

        cs.sub(
                m2 -> { cs.forEach(System.out::println); System.out.println(""); }
        );
        cs.sub(
                m2 -> System.out.println(cs.getMostCommon() + "\n")
        );

        ArrayList<IClock> clocks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clocks.add(cf.create(EClockTypes.THREE));
        }

        cs.addClock(cf.create(EClockTypes.TWO));
        cs.addClock(cf.create(EClockTypes.TWO));
        cs.addClock(cf.create(EClockTypes.TWO));

    }

}
