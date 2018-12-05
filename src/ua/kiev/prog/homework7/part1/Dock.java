package ua.kiev.prog.homework7.part1;

public class Dock {
    private boolean lock = false;
    private String name;
    private Ship locker;

    public Dock(String name) {
        this.name = name;
    }

    public void setBox(Box box) {
        if (box.getShipName().equals(locker.getName())) {
            System.out.println(box + "accepted to " + name);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isLocked() {
        return lock;
    }

    public void lock(Ship locker) {
        if (!lock) {
            lock = true;
            this.locker = locker;
        }
    }

    public void unLock(Ship locker) {
        if (lock && this.locker.getName().equals(locker.getName())) {
            lock = false;
        }
        System.out.println(locker.getName() + " released " + name);
    }
}
