package ua.kiev.prog.homework7.part1;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    List<Dock> docks = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void addDock(Dock dock) {
        docks.add(dock);
    }

    public Dock getDock(Ship ship) {
        Dock emptyDock;
        try {
            lock.lock();
            while (!isEmptyDocks()) {
                try {
                    System.out.println(ship.getName() + " waiting for empty dock.");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            emptyDock = getEmptyDock();
            emptyDock.lock(ship);
        } finally {
            lock.unlock();
        }
        return emptyDock;
    }

    private boolean isEmptyDocks() {
        for (Dock dock : docks) {
            if (!dock.isLocked()) {
                return true;
            }
        }
        return false;
    }

    private Dock getEmptyDock() {
        for (Dock dock : docks) {
            if (!dock.isLocked()) {
                return dock;
            }
        }
        return null;
    }

    public void leave() {
        try {
            lock.lock();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
