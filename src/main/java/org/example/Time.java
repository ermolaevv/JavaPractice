package org.example;

public class Time {
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;

    public Time() { }

    public Time(int hours, int minutes) throws Exception {
        if (hours < 0 || minutes < 0 || hours >= 60 || minutes >= 60) {
            throw new Exception("Wrong value");
        }
        this.hours = hours;
        this.minutes = minutes;
    }

    public Time(int hours, int minutes, int seconds) throws Exception {
        this(hours, minutes);
        if (seconds < 0 || seconds >= 60) {
            throw new Exception("Wrong value");
        }
        this.seconds = seconds;
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getSeconds() {
        return this.seconds;
    }
}