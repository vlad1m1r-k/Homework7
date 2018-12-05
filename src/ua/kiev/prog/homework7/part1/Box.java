package ua.kiev.prog.homework7.part1;

public class Box {
    private int number;
    private String shipName;

    public Box(String shipName, int number) {
        this.shipName = shipName;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getShipName() {
        return shipName;
    }

    @Override
    public String toString() {
        return "Box{" +
                "shipName='" + shipName + '\'' +
                ", number=" + number +
                '}';
    }
}
