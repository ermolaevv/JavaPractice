package org.example.sources;


public interface IClock {
    public double getPrice();

    public String getName();

    public String getTime();

    public void setStartTime(Time var1);
    public void timeShift(Time var1);
}