package org.example.sources;

public class Clock
        implements IClock {
    protected int hours = 0;
    protected int minutes = 0;
    protected String name = "";
    protected double price = 0.0;
    protected int ID;
    public Clock() {
    }


    public Clock(int ID, String name, double price) {
        this.ID = ID;
        this.name = name;
        this.price = price;
    }

    public Clock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String toString() {
        return this.name + String.format(" (%02d:%02d) ", this.hours, this.minutes) + this.price;
    }

    public double getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }
    public int getID() {
        return this.ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getTime() {
        return String.format("%02d:%02d", this.hours, this.minutes);
    }

    public void timeShift(Time time) {
        this.minutes += time.getMinutes();
        if (this.minutes >= 60) {
            this.hours += this.minutes / 60;
            this.minutes %= 60;
        }
        this.hours = (this.hours + time.getHours()) % 24;
    }

    public void setStartTime(Time time) {
        this.hours = time.getHours();
        this.minutes = time.getMinutes();
    }
}