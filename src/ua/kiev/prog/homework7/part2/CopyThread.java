package ua.kiev.prog.homework7.part2;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CopyThread implements Runnable {
    private RandomAccessFile source;
    private RandomAccessFile target;
    private long from;
    private long step;

    public CopyThread(RandomAccessFile source, RandomAccessFile target, long from, long to) {
        this.source = source;
        this.target = target;
        this.from = from;
        this.step = to;
    }

    @Override
    public void run() {
        byte[] data = new byte[(int)step];
        try {
            source.seek(from);
            source.read(data);
            target.seek(from);
            target.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
