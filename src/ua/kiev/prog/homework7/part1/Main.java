package ua.kiev.prog.homework7.part1;

public class Main {
    public static void main(String[] args) {
        Port port = new Port();
        port.addDock(new Dock("Dock #1"));
        port.addDock(new Dock("Dock #2"));
        for (int i = 0; i < 3; i++){
            new Thread(new Ship(port, "Ship #"+i)).start();
        }
    }
}
