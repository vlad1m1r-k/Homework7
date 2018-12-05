package ua.kiev.prog.homework7.part1;

public class Ship implements Runnable{
    private String name;
    private Box[] boxes = new Box[10];
    private Port port;
    private Dock dock;

    public Ship(Port port, String name) {
        this.port = port;
        this.name = name;
        for (int i = 0; i < 10; i++) {
            boxes[i] = new Box(name, i);
        }
    }

    @Override
    public void run() {
        System.out.println(name + " arrived.");
        dock = port.getDock(this);
        for (Box box: boxes) {
            dock.setBox(box);
        }
        dock.unLock(this);
        port.leave();
    }

    public String getName(){
        return name;
    }
}
