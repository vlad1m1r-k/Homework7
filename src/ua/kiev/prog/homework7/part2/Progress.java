package ua.kiev.prog.homework7.part2;

public class Progress {
    private float step;
    private float cost;

    public Progress(float steps) {
        this.cost = 100 / steps;
        this.step = 0;
    }

    public synchronized void step() {
        step += cost;
        System.out.print("\r" + (int)step + " %\r");
    }
}
