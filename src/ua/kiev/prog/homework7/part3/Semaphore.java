package ua.kiev.prog.homework7.part3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Semaphore {
    private int threadCount = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void addThread() {
        try {
            lock.lock();
            threadCount++;
            System.out.print("\rThreads " + threadCount + "   \r");
        } finally {
            lock.unlock();
        }
    }

    public void removeThread(){
        try {
            lock.lock();
            threadCount--;
            System.out.print("\rThreads " + threadCount + "   \r");
            if (threadCount <= 0) {
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void waitThreads() throws InterruptedException {
        try {
            lock.lock();
            while (threadCount > 0){
                condition.await();
            }
        } finally {
            lock.unlock();
        }
    }
}
