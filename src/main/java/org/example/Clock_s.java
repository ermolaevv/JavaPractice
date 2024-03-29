package org.example;

import org.example.Clock;
import org.example.Time;

public class Clock_s
        extends Clock {
    protected int seconds = 0;

    public Clock_s() {
    }

    public Clock_s(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return this.name + String.format(" (%02d:%02d:%02d) ", this.hours, this.minutes, this.seconds) + this.price;
    }

    @Override
    public void timeShift(Time time) {
        this.seconds += time.getSeconds();
        if (this.seconds >= 60) {
            super.timeShift(time);
            this.seconds %= 60;
        } else {
            super.timeShift(time);
        }
    }

    @Override
    public void setStartTime(Time time) {
        this.seconds = time.getSeconds();
        super.setStartTime(time);
    }
}