package org.example;

import org.example.Time;

public interface IClock {
    public double getPrice();

    public String getName();

    public void setStartTime(Time var1);

    public void timeShift(Time var1);
}