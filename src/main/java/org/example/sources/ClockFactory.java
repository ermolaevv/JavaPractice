package org.example.sources;

import java.util.Random;

public class ClockFactory {
    private final double minPrice = 1.0;
    private final double maxPrice = 10000.0;
    private static final Random rand = new Random();
    private static final String[] names = new String[]
    {
        "Casio",
        "Tissot",
        "Swatch",
        "Rolex"
    };

    public IClock create(EClockTypes type) {
        double randomDouble = minPrice + maxPrice * rand.nextDouble();
        double randomPrice = (double)Math.round(randomDouble * 100.0) / 100.0;
        int randomIndex = rand.nextInt(names.length);
        switch (type) {
            case TWO -> {
                return new Clock(names[randomIndex], randomPrice);
            }
            case THREE -> {
                return new Clock_s(names[randomIndex], randomPrice);
            }
            default -> { return null; }
        }
    }
}