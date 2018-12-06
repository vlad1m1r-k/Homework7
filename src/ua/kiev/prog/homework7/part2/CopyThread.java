package ua.kiev.prog.homework7.part2;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CopyThread implements Runnable {
    private RandomAccessFile source;
    private RandomAccessFile target;
    private long from;
    private long step;

    public CopyThread(RandomAccessFile source, RandomAccessFile target, long from, long step) {
        this.source = source;
        this.target = target;
        this.from = from;
        this.step = step;
    }

    @Override
    public void run() {
        try {
            CopyFile.write(target, from, CopyFile.read(source, from, step));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
